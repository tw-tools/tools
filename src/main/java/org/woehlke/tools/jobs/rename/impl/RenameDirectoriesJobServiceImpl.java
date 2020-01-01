package org.woehlke.tools.jobs.rename.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.woehlke.tools.config.properties.ApplicationProperties;
import org.woehlke.tools.model.config.JobEventSignal;
import org.woehlke.tools.model.config.JobEventType;
import org.woehlke.tools.model.entities.Job;
import org.woehlke.tools.model.entities.Logbuch;
import org.woehlke.tools.model.entities.RenamedOneDirectory;
import org.woehlke.tools.model.services.JobService;
import org.woehlke.tools.model.services.LogbuchServiceAsync;
import org.woehlke.tools.model.services.RenamedOneDirectoryServiceAsync;
import org.woehlke.tools.jobs.images.info.ImagesInfoJobBackendGateway;
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
public class RenameDirectoriesJobServiceImpl implements RenameDirectoriesJobService {

    private final ImagesInfoJobBackendGateway imagesInfoJobBackendGateway;
    private final ApplicationProperties properties;
    private final TraverseDirsService traverseDirsService;
    private final TraverseFilesService traverseFilesService;
    private final JobService jobService;
    private final RenamedOneDirectoryServiceAsync renamedOneDirectoryServiceAsync;
    private final LogbuchServiceAsync logbuchServiceAsync;

    @Autowired
    public RenameDirectoriesJobServiceImpl(
        ImagesInfoJobBackendGateway imagesInfoJobBackendGateway,
        ApplicationProperties properties,
        TraverseDirsService traverseDirsService,
        TraverseFilesService traverseFilesService,
        JobService jobService,
        RenamedOneDirectoryServiceAsync renamedOneDirectoryServiceAsync,
        LogbuchServiceAsync logbuchServiceAsync
    ) {
        this.imagesInfoJobBackendGateway = imagesInfoJobBackendGateway;
        this.properties = properties;
        this.traverseDirsService = traverseDirsService;
        this.traverseFilesService = traverseFilesService;
        this.jobService = jobService;
        this.renamedOneDirectoryServiceAsync = renamedOneDirectoryServiceAsync;
        this.logbuchServiceAsync = logbuchServiceAsync;
    }

    private Job job;

    public void setRootDirectory(Job job) {
        this.job=job;
        line();
        info(START,SET_ROOT_DIRECTORY);
        FileFilter fileFilter = new FileFilterFile();
        traverseDirsService.add(job,fileFilter);
        traverseFilesService.add(job,fileFilter);
        info(DONE,SET_ROOT_DIRECTORY);
        line();
    }

    @Override
    public void run() {
        line();
        signalJobStartToDb();
        info(START,RENAME_DIRECTORIES,job);
        info(START,TRAVERSE_DIRS,job);
        traverseDirsService.run();
        info(DONE,TRAVERSE_DIRS,job);
        rename();
        line();
        signalJobDoneToDb();
        line();
    }

    private void info(JobEventSignal jobEventSignal, JobEventType step){
        String msg = jobEventSignal.name() + " " + step.getHumanReadable() + " " + step.getJobCase();
        logger.info(msg);
    }

    private void info(JobEventSignal jobEventSignal, JobEventType step, Job myJob){
        info(jobEventSignal,step);
        if(this.properties.getDbActive()){
            String line ="";
            String category ="";
            JobEventType jobEventType = JobEventType.LOGUBUCH_EVENT;
            Logbuch jobEvent = new Logbuch(line, category, myJob, jobEventType, jobEventSignal);
            this.logbuchServiceAsync.add(jobEvent);
        }
    }

    private void signalJobStartToDb(){
        info(START, LOGUBUCH_EVENT,job);
        if(this.properties.getDbActive()) {
            job = jobService.start(job);
        }
    }

    private void signalJobDoneToDb(){
        info( DONE, LOGUBUCH_EVENT, job);
        if(this.properties.getDbActive()) {
            job = jobService.finish(job);
        }
    }

    /*
    private void renameFiles(Job myJob){
        info(START,RENAME_FILES, myJob);
        info(START,TRAVERSE_DIRS,myJob);
        traverseDirs.run();
        info(DONE,TRAVERSE_DIRS,myJob);
        info(START,TRAVERSE_FILES,myJob);
        traverseFiles.run();
        info(DONE,TRAVERSE_FILES,myJob);
        info(START,RENAME_FILES,myJob);
        rename(myJob);
        info(DONE,RENAME_FILES,myJob);
    }
*/

    private void rename() {
        info(START,RENAME_DIRECTORIES,job);
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
                    info(signal,event,job);
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
        info(DONE,RENAME_DIRECTORIES,job);
    }

    private void line(){
        logger.info("###############################################");
    }

    @Override
    public String getJobName() {
        return JOB_RENAME_FILES.getHumanReadable();
    }

    private Log logger = LogFactory.getLog(RenameFilesJobServiceImpl.class);
}
