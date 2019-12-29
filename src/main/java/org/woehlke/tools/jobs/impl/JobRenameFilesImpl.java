package org.woehlke.tools.jobs.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.woehlke.tools.config.ToolsApplicationProperties;
import org.woehlke.tools.db.*;
import org.woehlke.tools.db.services.JobService;
import org.woehlke.tools.db.services.JobStepService;
import org.woehlke.tools.jobs.common.FileFilterFile;
import org.woehlke.tools.jobs.common.LogbuchQueueService;
import org.woehlke.tools.jobs.common.FilenameTransform;
import org.woehlke.tools.jobs.mq.JobRenameFilesAsyncService;
import org.woehlke.tools.jobs.traverse.TraverseDirs;
import org.woehlke.tools.jobs.traverse.TraverseFiles;
import org.woehlke.tools.jobs.JobRenameFiles;

import java.io.File;
import java.io.FileFilter;
import java.util.Deque;

import static org.woehlke.tools.db.common.JobCase.JOB_RENAME_FILES;
import static org.woehlke.tools.jobs.impl.JobRenameStep.*;
import static org.woehlke.tools.jobs.impl.JobStepSignal.DONE;
import static org.woehlke.tools.jobs.impl.JobStepSignal.START;

@Component
public class JobRenameFilesImpl extends Thread implements JobRenameFiles {

    private final LogbuchQueueService log;
    private final TraverseDirs traverseDirs;
    private final TraverseFiles traverseFiles;
    //private final LogbuchService logbuchService;
    private final JobRenameFilesAsyncService jobRenameFilesAsyncService;
    private final JobService jobService;
    private final JobStepService jobStepService;
    private final boolean dryRun;
    private final boolean dbActive;
    private final ToolsApplicationProperties properties;
    private JobStepMessages msg;

    @Autowired
    public JobRenameFilesImpl(
        @Qualifier("jobRenameFilesQueueImpl") final LogbuchQueueService log,
        final TraverseDirs traverseDirs,
        final TraverseFiles traverseFiles,
        //final LogbuchService logbuchService,
        final JobRenameFilesAsyncService jobRenameFilesAsyncService,
        final JobService jobService,
        JobStepService jobStepService, ToolsApplicationProperties properties) {
        this.log = log;
        this.traverseDirs = traverseDirs;
        this.traverseFiles = traverseFiles;
        //this.logbuchService = logbuchService;
        this.jobRenameFilesAsyncService = jobRenameFilesAsyncService;
        this.jobService = jobService;
        this.jobStepService = jobStepService;
        this.properties = properties;
        this.dryRun = properties.getDryRun();
        this.dbActive = properties.getDbActive();
    }

    private void renameDirectories(Job myJob){
        info(START,RENAME_DIRECTORIES,myJob);
        info(START,TRAVERSE_DIRS,myJob);
        traverseDirs.run();
        info(DONE,TRAVERSE_DIRS,myJob);
        info(START,RENAME,myJob);
        rename(myJob);
        info(DONE,RENAME,myJob);
        info(DONE,RENAME_DIRECTORIES,myJob);
    }

    private String dataRootDir;

    public void setRootDirectory(File rootDirectory) {
        this.msg = new JobStepMessages(rootDirectory.getAbsolutePath());
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

    private void info(JobStepSignal jobStepSignal, JobRenameStep step, Job myJob){
        log.info(msg.get(jobStepSignal,step));
        if(this.dbActive){
            JobStep jobStep = JobStep.create(jobStepSignal, step, myJob, msg);
            jobStepService.add(jobStep);
        }
    }

    private Job signalJobStartToDb(){
        log.info(msg.get( DONE, JOB_RENAME_FILES));
        Job myJob = Job.create(JOB_RENAME_FILES,this.dataRootDir,this.dryRun,this.dbActive);
        if(this.dbActive) {
            myJob = jobService.start(myJob);
        }
        return myJob;
    }

    private void signalJobDoneToDb(Job myJob){
        log.info(msg.get( DONE, JOB_RENAME_FILES));
        if(this.dbActive) {
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
        rename(myJob);
        info(DONE,RENAME_FILES,myJob);
    }

    private void rename(Job myJob) {
        info(START,RENAME,myJob);
        Deque<File> stack =  this.traverseDirs.getResult();
        while (!stack.isEmpty()){
            File srcFile = stack.pop();
            String parentPath = srcFile.getParent();
            String oldFilename = srcFile.getName();
            String newFilename = FilenameTransform.getNewName(oldFilename);
            if(oldFilename.compareTo(newFilename)!=0){
                String newFilepath = parentPath + File.separator + newFilename;
                File targetFile = new File(newFilepath);
                String msg="RENAME: "+srcFile.getAbsolutePath()+" -> "+targetFile.getAbsolutePath();
                String category = "rename";
                log.info(msg,category, JOB_RENAME_FILES);
                if(dbActive){
                    Renamed p = new Renamed();
                    p.setJob(myJob);
                    p.setDirectory(srcFile.isDirectory());
                    p.setParent(srcFile.getParent());
                    p.setSource(srcFile.getName());
                    p.setTarget(targetFile.getName());
                    p.setDryRun(this.dryRun);
                    this.jobRenameFilesAsyncService.add(p);
                }
                if(!this.dryRun){
                    srcFile.renameTo(targetFile);
                }
            }
            try {
                Thread.sleep(100);
            } catch (Exception e){

            }
        }
        info(DONE,RENAME,myJob);
    }

    private void line(){
        log.info(msg.getLine());
    }

}
