package org.woehlke.tools.jobs.rename.impl;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.woehlke.tools.config.properties.ApplicationProperties;
import org.woehlke.tools.jobs.common.impl.AbstractJobServiceImpl;
import org.woehlke.tools.model.config.JobEventSignal;
import org.woehlke.tools.model.config.JobEventType;
import org.woehlke.tools.model.entities.Job;
import org.woehlke.tools.model.entities.RenamedOneDirectory;
import org.woehlke.tools.model.entities.RenamedOneFile;
import org.woehlke.tools.model.services.JobService;
import org.woehlke.tools.model.services.LogbuchServiceAsync;
import org.woehlke.tools.model.services.RenamedOneDirectoryServiceAsync;
import org.woehlke.tools.model.services.RenamedOneFileServiceAsync;
import org.woehlke.tools.jobs.images.info.ImagesInfoJobBackendGateway;
import org.woehlke.tools.jobs.rename.RenameFilesJobService;
import org.woehlke.tools.jobs.traverse.TraverseDirsService;
import org.woehlke.tools.jobs.traverse.TraverseFilesService;
import org.woehlke.tools.jobs.traverse.filter.FileFilterFile;

import java.io.File;
import java.io.FileFilter;
import java.util.Deque;

import static org.woehlke.tools.model.config.JobCase.JOB_RENAME_FILES;
import static org.woehlke.tools.model.config.JobEventSignal.*;
import static org.woehlke.tools.model.config.JobEventType.*;

@Service
public class RenameFilesJobServiceImpl extends AbstractJobServiceImpl implements RenameFilesJobService {

    private final ImagesInfoJobBackendGateway imagesInfoJobBackendGateway;
    private final TraverseDirsService traverseDirsService;
    private final TraverseFilesService traverseFilesService;
    private final JobService jobService;
    private final RenamedOneDirectoryServiceAsync renamedOneDirectoryServiceAsync;
    private final RenamedOneFileServiceAsync renamedOneFileServiceAsync;

    @Autowired
    public RenameFilesJobServiceImpl(
        final ImagesInfoJobBackendGateway imagesInfoJobBackendGateway,
        final TraverseDirsService traverseDirsService,
        final TraverseFilesService traverseFilesService,
        final JobService jobService,
        final RenamedOneDirectoryServiceAsync renamedOneDirectoryServiceAsync,
        final RenamedOneFileServiceAsync renamedOneFileServiceAsync,
        final ApplicationProperties properties,
        final LogbuchServiceAsync logbuchServiceAsync
    ) {
        super(logbuchServiceAsync,properties);
        this.imagesInfoJobBackendGateway = imagesInfoJobBackendGateway;
        this.traverseDirsService = traverseDirsService;
        this.traverseFilesService = traverseFilesService;
        this.jobService = jobService;
        this.renamedOneDirectoryServiceAsync = renamedOneDirectoryServiceAsync;
        this.renamedOneFileServiceAsync = renamedOneFileServiceAsync;
    }

    /*
    private void renameDirectories(Job myJob){
        info(START,RENAME_DIRECTORIES,myJob);
        info(START,TRAVERSE_DIRS,myJob);
        traverseDirs.run();
        info(DONE,TRAVERSE_DIRS,myJob);
        info(START,RENAME_DIRECTORIES,myJob);
        rename(myJob);
        info(DONE,RENAME_DIRECTORIES,myJob);
        info(DONE,RENAME_DIRECTORIES,myJob);
    }
    */

    private Job job;

    public void setRootDirectory(Job job) {
        line();
        info(START,SET_ROOT_DIRECTORY);
        this.job=job;
        FileFilter fileFilter = new FileFilterFile();
        traverseDirsService.add(this.job, fileFilter);
        traverseFilesService.add(this.job, fileFilter);
        info(DONE,SET_ROOT_DIRECTORY);
        line();
    }

    @Override
    public void run() {
        signalJobStartToDb();
        line();
        traverseDirsService.run();
        traverseFilesService.run();
        line();
        rename();
        line();
        signalJobDoneToDb();
        line();
    }

    private void signalJobStartToDb(){
        info(START,RENAME_FILES);
        if(this.properties.getDbActive()) {
            job = jobService.start(job);
        }
    }

    private void signalJobDoneToDb(){
        info( DONE, LOGUBUCH_EVENT);
        if(this.properties.getDbActive()) {
            job = jobService.finish(job);
        }
    }

    private void rename() {
        info(START,RENAME_FILES);
        Deque<File> stack =  this.traverseDirsService.getResult();
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
                        this.renamedOneDirectoryServiceAsync.add(je);
                    }
                } else {
                    JobEventSignal signal = DONE;
                    JobEventType event = JobEventType.RENAME_ONE_FILE;
                    if(this.properties.getDryRun()){
                        signal = DRY_RUN;
                    } else {
                        srcFile.renameTo(targetFile);
                    }
                    info(signal,event);
                    if(this.properties.getDbActive()) {
                        RenamedOneFile je = new RenamedOneFile(
                            srcFile,
                            targetFile,
                            job,
                            event,
                            signal
                        );
                        this.renamedOneFileServiceAsync.add(je);
                    }
                }
            }
            try {
                Thread.sleep(100);
            } catch (Exception e){ }
        }
        info(DONE,RENAME_FILES);
    }

    @Override
    public String getJobName() {
        return JOB_RENAME_FILES.getHumanReadable();
    }

    private Log logger = LogFactory.getLog(RenameFilesJobServiceImpl.class);
}
