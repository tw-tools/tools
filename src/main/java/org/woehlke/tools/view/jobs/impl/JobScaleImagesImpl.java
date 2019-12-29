package org.woehlke.tools.view.jobs.impl;


import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.woehlke.tools.config.application.ToolsApplicationProperties;
import org.woehlke.tools.model.db.entities.jobevents.ScaledImageJpg;
import org.woehlke.tools.model.db.entities.parts.JobEventScaledImageJpgFile;
import org.woehlke.tools.model.db.entities.Job;
import org.woehlke.tools.model.db.entities.jobevents.ScaleImagesJob;
import org.woehlke.tools.model.db.services.JobService;
import org.woehlke.tools.model.db.services.jobevents.ScaleImagesJobServiceAsync;
import org.woehlke.tools.model.db.services.jobevents.ScaledImageJpgServiceAsync;
import org.woehlke.tools.model.mq.images.JobScaleImagesQueue;
import org.woehlke.tools.model.traverse.filter.FileFilterImages;
import org.woehlke.tools.model.jobs.common.JobEventMessages;
import org.woehlke.tools.config.db.JobEventSignal;
import org.woehlke.tools.config.db.JobScaleImagesEvent;
import org.woehlke.tools.model.jobs.images.ShrinkJpgImage;
import org.woehlke.tools.model.traverse.TraverseDirs;
import org.woehlke.tools.model.traverse.TraverseFiles;
import org.woehlke.tools.view.jobs.JobScaleImages;

import java.io.File;
import java.io.FileFilter;
import java.util.Deque;

import static org.woehlke.tools.config.db.JobCase.JOB_SCALE_IMAGES;
import static org.woehlke.tools.config.db.JobScaleImagesEvent.*;
import static org.woehlke.tools.config.db.JobEventSignal.DONE;
import static org.woehlke.tools.config.db.JobEventSignal.START;

@Component
public class JobScaleImagesImpl  extends Thread implements JobScaleImages {

    private final JobScaleImagesQueue log;
    private final TraverseDirs traverseDirs;
    private final TraverseFiles traverseFiles;
    private final JobService jobService;
    private final ScaledImageJpgServiceAsync scaledImageJpgServiceAsync;
    private final ScaleImagesJobServiceAsync scaleImagesJobServiceAsync;
    private final ShrinkJpgImage shrinkJpgImage;
    private final ToolsApplicationProperties properties;
    private final JobEventMessages msg;

    @Autowired
    public JobScaleImagesImpl(
        final JobScaleImagesQueue log,
        final TraverseDirs traverseDirs,
        final TraverseFiles traverseFiles,
        final JobService jobService,
        final ScaledImageJpgServiceAsync scaledImageJpgServiceAsync,
        final ScaleImagesJobServiceAsync scaleImagesJobServiceAsync,
        final ShrinkJpgImage shrinkJpgImage,
        final ToolsApplicationProperties properties,
        final JobEventMessages jobEventMessages
    ) {
        this.log = log;
        this.traverseDirs = traverseDirs;
        this.traverseFiles = traverseFiles;
        this.jobService = jobService;
        this.scaledImageJpgServiceAsync = scaledImageJpgServiceAsync;
        this.scaleImagesJobServiceAsync = scaleImagesJobServiceAsync;
        this.shrinkJpgImage = shrinkJpgImage;
        this.properties = properties;
        this.msg = jobEventMessages;
    }

    private void line(){
        log.info("*********************");
    }

    private final Tika defaultTika = new Tika();
    private String dataRootDir;

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
        if(this.properties.getDbActive()){
            ScaleImagesJob jobEvent = new ScaleImagesJob(jobEventSignal, step, myJob, msg);
            this.scaleImagesJobServiceAsync.add(jobEvent);
        }
    }

    private Job signalJobStartToDb(){
        log.info(msg.get( START, JOB_SCALE_IMAGES));
        Job myJob = Job.create(
            JOB_SCALE_IMAGES,
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
        log.info(msg.get( DONE, JOB_SCALE_IMAGES));
        if(this.properties.getDbActive()) {
            jobService.finish(myJob);
        }
    }

     private void scaleJpgImages(Job myJob) {
        info( START,SCALE_JPG_IMAGES,myJob);
        Deque<File> stack =  this.traverseFiles.getResult();
        while (!stack.isEmpty()){
            File srcFile = stack.pop();
            File targetFile;
            JobEventSignal jobEventSignal = JobEventSignal.DO;
            if(this.properties.getDryRun()){
                targetFile = srcFile;
                jobEventSignal = JobEventSignal.DRR_RUN;
            } else {
                targetFile = shrinkJpgImage.shrienk(srcFile);
            }
            log.info(jobEventSignal.name() + " shrinkJpgImage: "+srcFile.getAbsolutePath());
            if(this.properties.getDbActive()){
                long length = 0L;
                long width = 0L;
                JobScaleImagesEvent jobRenameEvent = SCALE_IMAGE;
                JobEventScaledImageJpgFile src = new JobEventScaledImageJpgFile(srcFile,length, width);
                JobEventScaledImageJpgFile target = new JobEventScaledImageJpgFile(targetFile, length, width);
                ScaledImageJpg img = new ScaledImageJpg(
                    src,
                    target,
                    jobEventSignal,
                    jobRenameEvent,
                    myJob,
                    this.msg
                );
                this.scaledImageJpgServiceAsync.add(img);
            }
        }
        info( DONE,SCALE_JPG_IMAGES,myJob);
    }
}
