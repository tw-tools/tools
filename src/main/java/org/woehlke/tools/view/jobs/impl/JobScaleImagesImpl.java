package org.woehlke.tools.view.jobs.impl;


import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.woehlke.tools.config.ToolsApplicationProperties;
import org.woehlke.tools.model.db.ImageJpg;
import org.woehlke.tools.model.db.Job;
import org.woehlke.tools.model.db.JobEventScaleImagesJob;
import org.woehlke.tools.model.db.services.ImageJpgService;
import org.woehlke.tools.model.db.services.JobService;
import org.woehlke.tools.model.db.services.JobEventService;
import org.woehlke.tools.model.traverse.filter.FileFilterImages;
import org.woehlke.tools.model.jobs.common.JobEventMessages;
import org.woehlke.tools.model.db.config.JobEventSignal;
import org.woehlke.tools.model.jobs.images.common.JobScaleImagesEvent;
import org.woehlke.tools.model.jobs.images.ShrinkJpgImage;
import org.woehlke.tools.model.jobs.common.mq.LogbuchQueueService;
import org.woehlke.tools.model.traverse.TraverseDirs;
import org.woehlke.tools.model.traverse.TraverseFiles;
import org.woehlke.tools.view.jobs.JobScaleImages;

import java.io.File;
import java.io.FileFilter;
import java.util.Deque;

import static org.woehlke.tools.model.db.config.JobCase.JOB_SCALE_IMAGES;
import static org.woehlke.tools.model.jobs.images.common.JobScaleImagesEvent.*;
import static org.woehlke.tools.model.db.config.JobEventSignal.DONE;
import static org.woehlke.tools.model.db.config.JobEventSignal.START;

@Component
public class JobScaleImagesImpl  extends Thread implements JobScaleImages {

    private final LogbuchQueueService log;
    private final TraverseDirs traverseDirs;
    private final TraverseFiles traverseFiles;
    private final JobService jobService;
    private final JobEventService jobEventService;
    private final ShrinkJpgImage shrinkJpgImage;
    private final ImageJpgService imageJpgService;
    private final boolean dryRun;
    private final boolean dbActive;
    private final ToolsApplicationProperties properties;

    @Autowired
    public JobScaleImagesImpl(
        @Qualifier("jobScaleImagesQueueImpl") final LogbuchQueueService log,
        final TraverseDirs traverseDirs,
        final TraverseFiles traverseFiles,
        final JobService jobService,
        final JobEventService jobEventService,
        final ShrinkJpgImage shrinkJpgImage,
        final ImageJpgService imageJpgService,
        final ToolsApplicationProperties properties
    ) {
        this.log = log;
        this.traverseDirs = traverseDirs;
        this.traverseFiles = traverseFiles;
        this.jobService = jobService;
        this.jobEventService = jobEventService;
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
    private JobEventMessages msg;

    public void setRootDirectory(File rootDirectory) {
        this.msg.setRooTDirectory(rootDirectory);
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

    private void info(JobEventSignal jobEventSignal, JobScaleImagesEvent step, Job myJob){
        log.info(msg.get(jobEventSignal,step));
        if(this.dbActive){
            JobEventScaleImagesJob jobEvent = new JobEventScaleImagesJob(jobEventSignal, step, myJob, msg);
            jobEventService.add(jobEvent);
        }
    }

    private Job signalJobStartToDb(){
        log.info(msg.get( START, JOB_SCALE_IMAGES));
        Job myJob = Job.create(JOB_SCALE_IMAGES,this.dataRootDir,this.dryRun,this.dbActive);
        if(this.dbActive) {
            myJob = jobService.start(myJob);
        }
        return myJob;
    }

    private void signalJobDoneToDb(Job myJob){
        log.info(msg.get( DONE, JOB_SCALE_IMAGES));
        if(this.dbActive) {
            jobService.finish(myJob);
        }
    }

     private void scaleJpgImages(Job myJob) {
        info( START,SCALE_JPG_IMAGES,myJob);
        Deque<File> stack =  this.traverseFiles.getResult();
        while (!stack.isEmpty()){
            File srcFile = stack.pop();
            File targetFile;
            if(dryRun){
                log.info("fDryRun shrinkJpgImage: "+srcFile.getAbsolutePath());
                targetFile=srcFile;
            } else {
                log.info("shrinkJpgImage: "+srcFile.getAbsolutePath());
                targetFile = shrinkJpgImage.shrienk(srcFile);
            }
            if(this.dbActive){
                long length = 0L;
                long width = 0L;
                ImageJpg img = ImageJpg.create(targetFile, length, width);
                img.setJob(myJob);
                imageJpgService.add(img);
                //TODO: JobEvent
            }
        }
        info( DONE,SCALE_JPG_IMAGES,myJob);
    }
}
