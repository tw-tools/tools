package org.woehlke.tools.jobs.images.resize.impl;


import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.common.ImageMetadata;
import org.apache.commons.imaging.formats.jpeg.JpegImageMetadata;
import org.apache.commons.imaging.formats.tiff.TiffField;
import org.apache.commons.imaging.formats.tiff.constants.TiffTagConstants;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.woehlke.tools.config.properties.ApplicationProperties;
import org.woehlke.tools.config.properties.MmiProperties;
import org.woehlke.tools.model.db.config.JobEventType;
import org.woehlke.tools.model.db.entities.Logbuch;
import org.woehlke.tools.model.db.entities.ScaledImageJpg;
import org.woehlke.tools.model.db.entities.Job;
import org.woehlke.tools.model.db.services.JobService;
import org.woehlke.tools.model.db.services.LogbuchServiceAsync;
import org.woehlke.tools.model.db.services.ScaledImageJpgServiceAsync;
import org.woehlke.tools.jobs.images.resize.JobImagesResizeBackendGateway;
import org.woehlke.tools.jobs.traverse.filter.FileFilterImages;
import org.woehlke.tools.model.db.config.JobEventSignal;
import org.woehlke.tools.jobs.traverse.TraverseDirs;
import org.woehlke.tools.jobs.traverse.TraverseFiles;
import org.woehlke.tools.jobs.images.resize.JobImagesResizeJpg;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.Deque;

import static org.woehlke.tools.model.db.config.JobCase.JOB_IMAGES_RESIZE;
import static org.woehlke.tools.model.db.config.JobEventSignal.*;
import static org.woehlke.tools.model.db.config.JobEventType.*;

@Component
public class JobImagesResizeJpgImpl extends Thread implements JobImagesResizeJpg {

    private final JobImagesResizeBackendGateway jobImagesResizeBackendGateway;
    private final TraverseDirs traverseDirs;
    private final TraverseFiles traverseFiles;
    private final JobService jobService;
    private final ScaledImageJpgServiceAsync scaledImageJpgServiceAsync;
    private final LogbuchServiceAsync logbuchServiceAsync;
    private final ApplicationProperties cfg;
    private final MmiProperties properties;

    @Autowired
    public JobImagesResizeJpgImpl(
        final JobImagesResizeBackendGateway jobImagesResizeBackendGateway,
        final TraverseDirs traverseDirs,
        final TraverseFiles traverseFiles,
        final JobService jobService,
        final ScaledImageJpgServiceAsync scaledImageJpgServiceAsync,
        final LogbuchServiceAsync logbuchServiceAsync,
        final ApplicationProperties cfg,
        final MmiProperties properties
    ) {
        this.jobImagesResizeBackendGateway = jobImagesResizeBackendGateway;
        this.traverseDirs = traverseDirs;
        this.traverseFiles = traverseFiles;
        this.jobService = jobService;
        this.scaledImageJpgServiceAsync = scaledImageJpgServiceAsync;
        this.logbuchServiceAsync = logbuchServiceAsync;
        this.cfg = cfg;
        this.properties = properties;
    }

    private void line(){
        logger.info("*********************");
    }

    private final Tika defaultTika = new Tika();

    private Job job;

    public void setRootDirectory(Job job) {
        this.job=job;
        FileFilter fileFilter = new FileFilterImages();
        traverseDirs.add( this.job, fileFilter);
        traverseFiles.add( this.job, fileFilter);
    }

    @Override
    public String getJobName() {
        return JOB_IMAGES_RESIZE.getHumanReadable();
    }

    @Override
    public void run() {
        Job myJob = signalJobStartToDb();
        line();
        info( START, TRAVERSE_DIRS ,myJob);
        this.traverseDirs.run();
        info( DONE, TRAVERSE_DIRS ,myJob);
        line();
        info( START,TRAVERSE_FILES ,myJob);
        this.traverseFiles.run();
        info( DONE, TRAVERSE_FILES ,myJob);
        line();
        scaleJpgImages(myJob);
        line();
        signalJobDoneToDb(myJob);
    }

    private void info(
        JobEventSignal jobEventSignal,
        JobEventType step
    ){
        logger.info(jobEventSignal.name() +" "+ step.getHumanReadable()+ " "+ step.getJobCase().getHumanReadable());
    }

    private void info(
        JobEventSignal jobEventSignal,
        JobEventType step,
        Job myJob
    ){
        info(jobEventSignal,step);
        if(this.cfg.getDbActive()){
            String line = "TODO LINE";
            Logbuch jobEvent = new Logbuch(line,myJob,step,jobEventSignal);
            this.logbuchServiceAsync.add(jobEvent);
        }
    }

    private Job signalJobStartToDb(){
        if(this.cfg.getDbActive()) {
            job = jobService.start(job);
        }
        info(START, SCALE_JPG_IMAGES);
        return job;
    }

    private void signalJobDoneToDb(Job myJob){
        info(DONE, SCALE_JPG_IMAGES, myJob);
        if(this.cfg.getDbActive()) {
            jobService.finish(myJob);
        }
    }

     private void scaleJpgImages(Job myJob) {
        info( START, SCALE_JPG_IMAGES, myJob);
        Deque<File> stack =  this.traverseFiles.getResult();
        while (!stack.isEmpty()){
            File srcFile = stack.pop();
            ScaledImageJpg o = scaleOneImage(
                srcFile,
                myJob,
                START,
                SCALE_ONE_JPG_IMAGE);
            scaledImageJpgServiceAsync.add(o);
        }
        info( DONE,SCALE_JPG_IMAGES, myJob);
    }


    private ScaledImageJpg scaleOneImage(
        final File srcFile,
        Job myJob,
        JobEventSignal jobEventSignal,
        JobEventType jobEventType
    ) {
        logger.info("JPEG : "+srcFile.getAbsolutePath());
        File srcFileCopy = new File(srcFile.getAbsolutePath());
        long width = 0L;
        long length = 0L;
        JpegImageMetadata jpegMetadata = getImageMetadata(srcFileCopy);
        if (jpegMetadata != null) {
            width = getWidth(jpegMetadata);
            length = getLength(jpegMetadata);
        }
        ScaledImageJpg jpgFile = new ScaledImageJpg(
             srcFile,
             length,
             width,
             jobEventSignal,
             jobEventType,
             myJob
        );
        performTheShrienking(jpgFile);
        srcFileCopy = new File(srcFile.getAbsolutePath());
        jpegMetadata = getImageMetadata(srcFileCopy);
        if (jpegMetadata != null) {
            jpgFile.setWidthScaled( getWidth(jpegMetadata) );
            jpgFile.setLengthScaled( getLength(jpegMetadata) );
        }
        return jpgFile;
    }

    private JpegImageMetadata getImageMetadata(File srcFileCopy){
        JpegImageMetadata jpegMetadata = null;
        ImageMetadata metadata = null;
        try {
            metadata = Imaging.getMetadata(srcFileCopy);
        } catch (NullPointerException | ImageReadException | IOException e) {
            logger.warn(e.getMessage());
        }
        if ((metadata != null) && (metadata instanceof JpegImageMetadata)) {
            jpegMetadata = (JpegImageMetadata) metadata;
        }
        return jpegMetadata;
    }

    private long getWidth(final JpegImageMetadata jpegMetadata){
        long width = 0L;
        try {
            final TiffField fieldWidth = jpegMetadata.findEXIFValueWithExactMatch(
                TiffTagConstants.TIFF_TAG_IMAGE_WIDTH
            );
            if (fieldWidth != null) {
                width = fieldWidth.getIntValue();
            }
        } catch (NullPointerException | ImageReadException e) {
            logger.warn(e.getMessage());
        }
        return width;
    }

    private long getLength(final JpegImageMetadata jpegMetadata){
        long length = 0L;
        try {
            final TiffField fieldLength = jpegMetadata.findEXIFValueWithExactMatch(
                TiffTagConstants.TIFF_TAG_IMAGE_LENGTH
            );
            if (fieldLength != null) {
                length = fieldLength.getIntValue();
            }
        } catch (NullPointerException | ImageReadException e) {
            logger.warn(e.getMessage());
        }
        return length;
    }

    private ScaledImageJpg performTheShrienking(ScaledImageJpg jpgFile){
        final String srcPath = jpgFile.getFilepath();
        final String tmpFilePath = srcPath + "_bak.jpg";
        int targetScale = 1600;
        int resizeFactorAsPercent = jpgFile.getScaleFactorAsPercent(targetScale);
        String command = "magick convert " + srcPath + " -resize " + resizeFactorAsPercent + "% -density 72x72 " + tmpFilePath;
        jpgFile.setCommand(command);
        logger.debug(command);
        if (cfg.getDryRun()) {
            jpgFile.setJobEventSignal(JobEventSignal.DRY_RUN);
        } else {
            try {
                Process process = Runtime.getRuntime().exec(command);
                process.waitFor();
                File srcFile = new File(srcPath);
                File tmpFile = new File(tmpFilePath);
                srcFile.delete();
                srcFile = new File(srcPath);
                tmpFile.renameTo(srcFile);
                jpgFile.setJobEventSignal(JobEventSignal.DONE);
            } catch (IOException | InterruptedException e) {
                jpgFile.setJobEventSignal(JobEventSignal.ERROR);
                jpgFile.setResultMessage(e.getMessage());
            }
        }
        return jpgFile;
    }

    private Log logger = LogFactory.getLog(JobImagesResizeJpgImpl.class);
}
