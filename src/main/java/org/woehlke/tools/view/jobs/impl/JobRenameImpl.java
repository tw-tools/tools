package org.woehlke.tools.view.jobs.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.woehlke.tools.config.application.ToolsApplicationProperties;
import org.woehlke.tools.config.db.JobEventSignal;
import org.woehlke.tools.model.db.entities.Job;
import org.woehlke.tools.model.db.entities.jobevents.RenameFilesJob;
import org.woehlke.tools.model.db.entities.jobevents.RenamedOneDirectory;
import org.woehlke.tools.model.db.entities.jobevents.RenamedOneFile;
import org.woehlke.tools.model.db.services.JobService;
import org.woehlke.tools.model.db.services.jobevents.RenameFilesJobServiceAsync;
import org.woehlke.tools.model.db.services.jobevents.RenamedOneDirectoryServiceAsync;
import org.woehlke.tools.model.db.services.jobevents.RenamedOneFileServiceAsync;
import org.woehlke.tools.model.jobs.common.*;
import org.woehlke.tools.view.jobs.JobRename;
import org.woehlke.tools.config.db.JobRenameEvent;
import org.woehlke.tools.config.db.FilenameTransform;
import org.woehlke.tools.model.jobs.common.LogbuchQueueService;
import org.woehlke.tools.model.traverse.TraverseDirs;
import org.woehlke.tools.model.traverse.TraverseFiles;
import org.woehlke.tools.model.traverse.filter.FileFilterFile;

import java.io.File;
import java.io.FileFilter;
import java.util.Deque;

import static org.woehlke.tools.config.db.JobCase.JOB_RENAME_FILES;
import static org.woehlke.tools.config.db.JobEventSignal.*;
import static org.woehlke.tools.config.db.JobRenameEvent.*;

@Component
public class JobRenameImpl extends Thread implements JobRename {

    private final LogbuchQueueService log;
    private final TraverseDirs traverseDirs;
    private final TraverseFiles traverseFiles;
    private final JobService jobService;
    private final RenameFilesJobServiceAsync renameFilesJobServiceAsync;
    private final RenamedOneDirectoryServiceAsync renamedOneDirectoryServiceAsync;
    private final RenamedOneFileServiceAsync RenamedOneFileServiceAsync;
    private final ToolsApplicationProperties properties;
    private final JobEventMessages msg;

    @Autowired
    public JobRenameImpl(
        @Qualifier("jobRenameFilesQueueImpl") final LogbuchQueueService log,
        final TraverseDirs traverseDirs,
        final TraverseFiles traverseFiles,
        final JobService jobService,
        final RenameFilesJobServiceAsync renameFilesJobServiceAsync,
        final RenamedOneDirectoryServiceAsync renamedOneDirectoryServiceAsync,
        final RenamedOneFileServiceAsync renamedOneFileServiceAsync,
        final ToolsApplicationProperties properties,
        final JobEventMessages msg
    ) {
        this.log = log;
        this.traverseDirs = traverseDirs;
        this.traverseFiles = traverseFiles;
        this.jobService = jobService;
        this.renameFilesJobServiceAsync = renameFilesJobServiceAsync;
        this.renamedOneDirectoryServiceAsync = renamedOneDirectoryServiceAsync;
        this.RenamedOneFileServiceAsync = renamedOneFileServiceAsync;
        this.properties = properties;
        this.msg = msg;
    }

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

    private String dataRootDir;

    public void setRootDirectory(File rootDirectory) {
        this.msg.setRooTDirectory(rootDirectory);
        line();
        log.info(msg.get(START,SET_ROOT_DIRECTORY));
        this.dataRootDir = rootDirectory.getAbsolutePath();
        FileFilter fileFilter = new FileFilterFile();
        traverseDirs.add(this.dataRootDir,log,fileFilter);
        traverseFiles.add(this.dataRootDir,log,fileFilter);
        log.info(msg.get(DONE,SET_ROOT_DIRECTORY));
        line();
    }

    @Override
    public void run() {
        line();
        Job myJob = signalJobStartToDb();
        line();
        renameDirectories(  myJob);
        line();
        renameFiles(  myJob);
        line();
        signalJobDoneToDb(myJob);
        line();
    }

    private void info(JobEventSignal jobEventSignal, JobRenameEvent step, Job myJob){
        log.info(msg.get(jobEventSignal,step));
        if(this.properties.getDbActive()){
            RenameFilesJob jobEvent = new RenameFilesJob(jobEventSignal, step, myJob, msg);
            this.renameFilesJobServiceAsync.add(jobEvent);
        }
    }

    private Job signalJobStartToDb(){
        log.info(msg.get( DONE, JOB_RENAME_FILES));
        Job myJob = Job.create(
            JOB_RENAME_FILES,
            this.dataRootDir,
            this.properties.getDryRun(),
            this.properties.getDbActive()
        );
        if(this.properties.getDbActive()) {
            myJob = jobService.start(myJob);
        }
        return myJob;
    }

    private void signalJobDoneToDb(Job myJob){
        log.info(msg.get( DONE, JOB_RENAME_FILES));
        if(this.properties.getDbActive()) {
            jobService.finish(myJob);
        }
    }

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
                    JobEventSignal signal = DO;
                    if(!this.properties.getDryRun()){
                        srcFile.renameTo(targetFile);
                    } else {
                        signal = DRR_RUN;
                    }
                    log.info(this.msg.get(signal,RENAME_ONE_DIRECTORY));
                    if(this.properties.getDbActive()) {
                        RenamedOneDirectory je = new RenamedOneDirectory(
                            signal,
                            myJob,
                            srcFile,
                            targetFile
                        );
                        this.renamedOneDirectoryServiceAsync.add(je);
                    }
                } else {
                    JobEventSignal signal = DO;
                    if(!this.properties.getDryRun()){
                        srcFile.renameTo(targetFile);
                    } else {
                        signal = DRR_RUN;
                    }
                    log.info(this.msg.get(signal,RENAME_ONE_FILE));
                    if(this.properties.getDbActive()) {
                        RenamedOneFile je = new RenamedOneFile(
                            signal,
                            myJob,
                            srcFile,
                            targetFile
                        );
                        this.RenamedOneFileServiceAsync.add(je);
                    }
                }
            }
            try {
                Thread.sleep(100);
            } catch (Exception e){ }
        }
    }

    private void line(){
        log.info(msg.getLine());
    }

}
