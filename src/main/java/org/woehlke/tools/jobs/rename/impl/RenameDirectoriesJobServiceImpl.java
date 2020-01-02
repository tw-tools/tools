package org.woehlke.tools.jobs.rename.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.woehlke.tools.config.properties.ApplicationProperties;
import org.woehlke.tools.jobs.common.impl.AbstractJobServiceImpl;
import org.woehlke.tools.jobs.traverse.filter.FileFilterToBeRenamedDirectory;
import org.woehlke.tools.jobs.traverse.filter.FileFilterToBeRenamedFile;
import org.woehlke.tools.model.config.JobEventSignal;
import org.woehlke.tools.model.config.JobEventType;
import org.woehlke.tools.model.entities.Job;
import org.woehlke.tools.model.entities.RenamedOneDirectory;
import org.woehlke.tools.model.services.JobService;
import org.woehlke.tools.model.services.LogbuchServiceAsync;
import org.woehlke.tools.model.services.RenamedOneDirectoryServiceAsync;
import org.woehlke.tools.jobs.traverse.TraverseDirsService;
import org.woehlke.tools.jobs.traverse.TraverseFilesService;
import org.woehlke.tools.jobs.traverse.filter.FileFilterFile;
import org.woehlke.tools.jobs.rename.RenameDirectoriesJobService;

import java.io.File;
import java.io.FileFilter;
import java.util.Deque;

import static org.woehlke.tools.model.config.JobCase.JOB_RENAME_FILES;
import static org.woehlke.tools.model.config.JobEventSignal.*;
import static org.woehlke.tools.model.config.JobEventType.*;

@Service
public class RenameDirectoriesJobServiceImpl extends AbstractJobServiceImpl implements RenameDirectoriesJobService {

    private final TraverseDirsService traverseDirsService;
    private final TraverseFilesService traverseFilesService;
    private final RenamedOneDirectoryServiceAsync renamedOneDirectoryServiceAsync;

    @Autowired
    public RenameDirectoriesJobServiceImpl(
        ApplicationProperties properties,
        TraverseDirsService traverseDirsService,
        TraverseFilesService traverseFilesService,
        JobService jobService,
        RenamedOneDirectoryServiceAsync renamedOneDirectoryServiceAsync,
        LogbuchServiceAsync logbuchServiceAsync
    ) {
        super(logbuchServiceAsync, jobService, traverseDirsService, traverseFilesService, properties);
        this.traverseDirsService = traverseDirsService;
        this.traverseFilesService = traverseFilesService;
        this.renamedOneDirectoryServiceAsync = renamedOneDirectoryServiceAsync;
    }

    @Override
    public void setRootDirectory(Job job) {
        info(START,SET_ROOT_DIRECTORY);
        line();
        this.job=job;
        FileFilter fileFilterToBeRenamedDirectory = new FileFilterToBeRenamedDirectory();
        FileFilter fileFilterToBeRenamedFile = new FileFilterToBeRenamedFile();
        traverseDirsService.add(job,fileFilterToBeRenamedDirectory);
        traverseFilesService.add(job,fileFilterToBeRenamedFile);
        info(DONE,SET_ROOT_DIRECTORY);
        line();
    }

    @Override
    public void run() {
        line();
        signalJobStartToDb(RENAME_DIRECTORIES);
        traverseDirsService.run();
        rename();
        line();
        signalJobDoneToDb(RENAME_DIRECTORIES);
        line();
    }

    private void rename() {
        info(START,RENAME_DIRECTORIES);
        Deque<File> stack = this.traverseDirsService.getResult();
        while (!stack.isEmpty()){
            File srcFile = stack.pop();
            String parentPath = srcFile.getParent();
            String oldFilename = srcFile.getName();
            String newFilename = FilenameTransform.getNewName(oldFilename);
            if(oldFilename.compareTo(newFilename)!=0){
                String newFilepath = parentPath + File.separator + newFilename;
                File targetFile = new File(newFilepath);
                if(srcFile.isDirectory()){
                    JobEventSignal signal = JobEventSignal.DONE;
                    JobEventType event = JobEventType.RENAME_ONE_DIRECTORY;
                    if(this.properties.getDryRun()){
                        signal = DRY_RUN;
                    } else {
                        srcFile.renameTo(targetFile);
                    }
                    info(signal,event);
                    if(this.properties.getDbActive()) {
                        RenamedOneDirectory je = new RenamedOneDirectory(
                            srcFile,
                            targetFile,
                            job,
                            event,
                            signal
                        );
                        this.renamedOneDirectoryServiceAsync.sendMessage(je);
                        this.renamedOneDirectoryServiceAsync.add(je);
                    }
                } /*
                else {
                    JobEventSignal signal = DONE;
                    JobEventType event = JobEventType.RENAME_ONE_FILE;
                    if(this.properties.getDryRun()){
                        signal = DRY_RUN;
                    } else {
                        srcFile.renameTo(targetFile);
                    }
                    info(signal,event,myJob);
                    if(this.properties.getDbActive()) {
                        RenamedOneFile je = new RenamedOneFile(
                            srcFile,
                            targetFile,
                            myJob,
                            event,
                            signal
                        );
                        this.renamedOneFileServiceAsync.add(je);
                    }
                }*/
            }
            try {
                Thread.sleep(100);
            } catch (Exception e){ }
        }
        info(DONE,RENAME_DIRECTORIES);
    }

    @Override
    public String getJobName() {
        return JOB_RENAME_FILES.getHumanReadable();
    }

    private Log logger = LogFactory.getLog(RenameFilesJobServiceImpl.class);
}
