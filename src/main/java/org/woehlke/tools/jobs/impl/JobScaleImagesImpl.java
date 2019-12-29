package org.woehlke.tools.jobs.impl;


import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.woehlke.tools.config.ToolsApplicationProperties;
import org.woehlke.tools.db.ImageJpg;
import org.woehlke.tools.db.Job;
import org.woehlke.tools.db.JobStep;
import org.woehlke.tools.db.services.ImageJpgService;
import org.woehlke.tools.db.services.JobService;
import org.woehlke.tools.db.services.JobStepService;
import org.woehlke.tools.jobs.common.FileFilterImages;
import org.woehlke.tools.jobs.images.ShrinkJpgImage;
import org.woehlke.tools.jobs.common.LogbuchQueueService;
import org.woehlke.tools.jobs.traverse.TraverseDirs;
import org.woehlke.tools.jobs.traverse.TraverseFiles;
import org.woehlke.tools.jobs.JobScaleImages;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.Deque;

import static org.woehlke.tools.db.common.JobCase.JOB_SCALE_IMAGES;
import static org.woehlke.tools.jobs.impl.JobScaleImagesStep.*;
import static org.woehlke.tools.jobs.impl.JobStepSignal.DONE;
import static org.woehlke.tools.jobs.impl.JobStepSignal.START;

@Component
public class JobScaleImagesImpl  extends Thread implements JobScaleImages {

    private final LogbuchQueueService log;
    private final TraverseDirs traverseDirs;
    private final TraverseFiles traverseFiles;
    private final JobService jobService;
    private final JobStepService jobStepService;
    private final ShrinkJpgImage shrinkJpgImage;
    private final ImageJpgService imageJpgService;
    private final boolean dryRun;
    private final boolean dbActive;
    private final ToolsApplicationProperties properties;

    @Autowired
    public JobScaleImagesImpl(@Qualifier("jobScaleImagesQueueImpl") final LogbuchQueueService log,
                              final TraverseDirs traverseDirs,
                              final TraverseFiles traverseFiles,
                              final JobService jobService,
                              JobStepService jobStepService, final ShrinkJpgImage shrinkJpgImage,
                              final ImageJpgService imageJpgService,
                              final ToolsApplicationProperties properties) {
        this.log = log;
        this.traverseDirs = traverseDirs;
        this.traverseFiles = traverseFiles;
        this.jobService = jobService;
        this.jobStepService = jobStepService;
        this.shrinkJpgImage = shrinkJpgImage;
        this.imageJpgService = imageJpgService;
        this.properties = properties;
        this.dryRun = properties.getDryRun();
        this.dbActive = properties.getDbActive();
    }

    private void line(){
        log.info("*********************");
    }

    private final Tika defaultTika = new Tika();
    private String dataRootDir;
    private JobStepMessages msg;

    public void setRootDirectory(File rootDirectory) {
        this.msg = new JobStepMessages(rootDirectory.getAbsolutePath());
        this.dataRootDir = rootDirectory.getAbsolutePath();
        FileFilter fileFilter = new FileFilterImages();
        traverseDirs.add(this.dataRootDir, this.log, fileFilter);
        traverseFiles.add(this.dataRootDir, this.log, fileFilter);
    }

    @Override
    public void run() {
        Job myJob = signalJobStartToDb();
        line();
        info( START,TRAVERSE_DIRS,myJob);
        this.traverseDirs.run();
        info( DONE,TRAVERSE_DIRS,myJob);
        line();
        info( START,TRAVERSE_FILES,myJob);
        this.traverseFiles.run();
        info( DONE,TRAVERSE_FILES,myJob);
        line();
        scaleJpgImages(myJob);
        line();
        signalJobDoneToDb(myJob);
    }

    private void info(JobStepSignal jobStepSignal, JobScaleImagesStep step, Job myJob){
        log.info(msg.get(jobStepSignal,step));
        if(this.dbActive){
            JobStep jobStep = JobStep.create(jobStepSignal, step, myJob, msg);
            jobStepService.add(jobStep);
        }
    }

    private Job signalJobStartToDb(){
        log.info(msg.get( START, JOB));
        Job myJob = Job.create(JOB_SCALE_IMAGES,this.dataRootDir,this.dryRun,this.dbActive);
        if(this.dbActive) {
            myJob = jobService.start(myJob);
        }
        return myJob;
    }

    private void signalJobDoneToDb(Job myJob){
        log.info(msg.get( DONE, JOB));
        if(this.dbActive) {
            jobService.finish(myJob);
        }
    }

     private void scaleJpgImages(Job myJob) {
         info( START,SCALE_JPG_IMAGES,myJob);
        Deque<File> stack =  this.traverseFiles.getResult();
        while (!stack.isEmpty()){
            File srcFile = stack.pop();
            String fileType = "unknown";
            try {
                fileType = defaultTika.detect(srcFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(fileType.compareTo("image/jpeg")==0){
                File targetFile;
                if(dryRun){
                    log.info("fileType: "+ fileType + " DryRun  shrinkJpgImage: "+srcFile.getAbsolutePath());
                    targetFile=srcFile;
                } else {
                    log.info("fileType: "+ fileType + " Perform shrinkJpgImage: "+srcFile.getAbsolutePath());
                    targetFile = shrinkJpgImage.shrienk(srcFile);
                }
                if(this.dbActive){
                    long length = 0L;
                    long width = 0L;
                    ImageJpg img = ImageJpg.create(targetFile, length, width);
                    img.setJob(myJob);
                    imageJpgService.add(img);
                }
            } else {
                log.info("fileType: "+fileType+" - "+srcFile.getAbsolutePath());
            }
        }
         info( DONE,SCALE_JPG_IMAGES,myJob);
    }
}
