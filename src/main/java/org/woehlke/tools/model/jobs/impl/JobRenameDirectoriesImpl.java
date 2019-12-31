package org.woehlke.tools.model.jobs.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.woehlke.tools.config.properties.ToolsApplicationProperties;
import org.woehlke.tools.config.db.FilenameTransform;
import org.woehlke.tools.config.db.JobEventSignal;
import org.woehlke.tools.config.db.JobEventType;
import org.woehlke.tools.model.db.entities.Job;
import org.woehlke.tools.model.db.entities.Logbuch;
import org.woehlke.tools.model.db.entities.RenamedOneDirectory;
import org.woehlke.tools.model.db.services.JobService;
import org.woehlke.tools.model.db.services.LogbuchServiceAsync;
import org.woehlke.tools.model.db.services.RenamedOneDirectoryServiceAsync;
import org.woehlke.tools.model.mq.LogbuchQueue;
import org.woehlke.tools.model.mq.RenameQueue;
import org.woehlke.tools.model.traverse.TraverseDirs;
import org.woehlke.tools.model.traverse.TraverseFiles;
import org.woehlke.tools.model.traverse.filter.FileFilterFile;
import org.woehlke.tools.model.jobs.JobRenameDirectories;

import java.io.File;
import java.io.FileFilter;
import java.util.Deque;

import static org.woehlke.tools.config.db.JobCase.JOB_RENAME_FILES;
import static org.woehlke.tools.config.db.JobEventSignal.*;
import static org.woehlke.tools.config.db.JobEventType.*;

@Component
public class JobRenameDirectoriesImpl implements JobRenameDirectories {

    private final RenameQueue renameQueue;
    private final LogbuchQueue logbuchQueue;
    private final ToolsApplicationProperties properties;
    private final TraverseDirs traverseDirs;
    private final TraverseFiles traverseFiles;
    private final JobService jobService;
    private final RenamedOneDirectoryServiceAsync renamedOneDirectoryServiceAsync;
    private final LogbuchServiceAsync logbuchServiceAsync;

    @Autowired
    public JobRenameDirectoriesImpl(
        RenameQueue renameQueue,
        LogbuchQueue logbuchQueue,
        ToolsApplicationProperties properties,
        TraverseDirs traverseDirs,
        TraverseFiles traverseFiles,
        JobService jobService,
        RenamedOneDirectoryServiceAsync renamedOneDirectoryServiceAsync,
        LogbuchServiceAsync logbuchServiceAsync
    ) {
        this.renameQueue = renameQueue;
        this.logbuchQueue = logbuchQueue;
        this.properties = properties;
        this.traverseDirs = traverseDirs;
        this.traverseFiles = traverseFiles;
        this.jobService = jobService;
        this.renamedOneDirectoryServiceAsync = renamedOneDirectoryServiceAsync;
        this.logbuchServiceAsync = logbuchServiceAsync;
    }

    private String dataRootDir;

    public void setRootDirectory(File rootDirectory) {
        line();
        info(START,SET_ROOT_DIRECTORY);
        this.dataRootDir = rootDirectory.getAbsolutePath();
        FileFilter fileFilter = new FileFilterFile();
        traverseDirs.add(this.dataRootDir,fileFilter);
        traverseFiles.add(this.dataRootDir,fileFilter);
        info(DONE,SET_ROOT_DIRECTORY);
        line();
    }

    @Override
    public void run() {
        line();
        Job myJob = signalJobStartToDb();
        line();
        info(START,RENAME_DIRECTORIES,myJob);
        info(START,TRAVERSE_DIRS,myJob);
        traverseDirs.run();
        info(DONE,TRAVERSE_DIRS,myJob);
        info(START,RENAME_DIRECTORIES,myJob);
        rename(myJob);
        info(DONE,RENAME_DIRECTORIES,myJob);
        info(DONE,RENAME_DIRECTORIES,myJob);
        //line();
        //renameFiles(  myJob);
        line();
        signalJobDoneToDb(myJob);
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

    private Job signalJobStartToDb(){
        Job myJob = Job.create(
            JOB_RENAME_FILES,
            this.dataRootDir,
            this.properties.getDryRun(),
            this.properties.getDbActive()
        );
        info(START, LOGUBUCH_EVENT,myJob);
        if(this.properties.getDbActive()) {
            myJob = jobService.start(myJob);
        }
        return myJob;
    }

    private void signalJobDoneToDb(Job myJob){
        info( DONE, LOGUBUCH_EVENT, myJob);
        if(this.properties.getDbActive()) {
            jobService.finish(myJob);
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

    private void rename(Job myJob) {
        Deque<File> stack =  this.traverseDirs.getResult();
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
                    info(signal,event,myJob);
                    if(this.properties.getDbActive()) {
                        RenamedOneDirectory je = new RenamedOneDirectory(
                            srcFile,
                            targetFile,
                            myJob,
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
    }

    private void line(){
        logger.info("###############################################");
    }

    @Override
    public String getJobName() {
        return JOB_RENAME_FILES.getHumanReadable();
    }

    private Log logger = LogFactory.getLog(JobRenameFilesImpl.class);
}
