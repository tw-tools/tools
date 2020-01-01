package org.woehlke.tools.jobs.images.resize.impl;


import org.apache.commons.imaging.formats.jpeg.JpegImageMetadata;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.woehlke.tools.config.properties.ApplicationProperties;
import org.woehlke.tools.jobs.common.impl.AbstractJobServiceImpl;
import org.woehlke.tools.jobs.images.common.InfoImageJpegService;
import org.woehlke.tools.model.entities.ScaledImageJpg;
import org.woehlke.tools.model.entities.Job;
import org.woehlke.tools.model.services.JobService;
import org.woehlke.tools.model.services.LogbuchServiceAsync;
import org.woehlke.tools.model.services.ScaledImageJpgServiceAsync;
import org.woehlke.tools.jobs.traverse.filter.FileFilterImages;
import org.woehlke.tools.model.config.JobEventSignal;
import org.woehlke.tools.jobs.traverse.TraverseDirsService;
import org.woehlke.tools.jobs.traverse.TraverseFilesService;
import org.woehlke.tools.jobs.images.resize.JobImagesResizeJpgService;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.Deque;

import static org.woehlke.tools.model.config.JobCase.JOB_IMAGES_RESIZE;
import static org.woehlke.tools.model.config.JobEventSignal.*;
import static org.woehlke.tools.model.config.JobEventType.*;

@Service
public class JobImagesResizeJpgServiceImpl extends AbstractJobServiceImpl implements JobImagesResizeJpgService {

    private final ScaledImageJpgServiceAsync scaledImageJpgServiceAsync;
    private final InfoImageJpegService infoImageJpegService;

    @Autowired
    public JobImagesResizeJpgServiceImpl(
        final TraverseDirsService traverseDirsService,
        final TraverseFilesService traverseFilesService,
        final JobService jobService,
        final ScaledImageJpgServiceAsync scaledImageJpgServiceAsync,
        final LogbuchServiceAsync logbuchServiceAsync,
        final ApplicationProperties properties,
        final InfoImageJpegService infoImageJpegService
    ) {
        super(
            logbuchServiceAsync,
            jobService,
            traverseDirsService,
            traverseFilesService,
            properties
        );
        this.scaledImageJpgServiceAsync = scaledImageJpgServiceAsync;
        this.infoImageJpegService = infoImageJpegService;
    }

    public void setRootDirectory(Job job) {
        this.job=job;
        FileFilter fileFilter = new FileFilterImages();
        traverseDirsService.add( this.job, fileFilter);
        traverseFilesService.add( this.job, fileFilter);
    }

    @Override
    public void run() {
        signalJobStartToDb(SCALE_JPG_IMAGES);
        line();
        this.traverseDirsService.run();
        line();
        this.traverseFilesService.run();
        line();
        scaleJpgImages();
        line();
        signalJobDoneToDb(SCALE_JPG_IMAGES);
    }

     private void scaleJpgImages() {
        info( START, SCALE_JPG_IMAGES);
        Deque<File> stack =  this.traverseFilesService.getResult();
        while (!stack.isEmpty()){
            File srcFile = stack.pop();
            ScaledImageJpg o = scaleOneImage(
                srcFile,
                job);
            scaledImageJpgServiceAsync.sendMessage(o);
            scaledImageJpgServiceAsync.add(o);
        }
        info( DONE,SCALE_JPG_IMAGES);
    }

    private ScaledImageJpg scaleOneImage(
        final File srcFile,
        Job myJob
    ) {
        logger.info("JPEG : "+srcFile.getAbsolutePath());
        File srcFileCopy = new File(srcFile.getAbsolutePath());
        long width = 0L;
        long length = 0L;
        JpegImageMetadata jpegMetadata = infoImageJpegService.getImageMetadata(srcFileCopy);
        if (jpegMetadata != null) {
            width = infoImageJpegService.getWidth(jpegMetadata);
            length = infoImageJpegService.getLength(jpegMetadata);
        }
        ScaledImageJpg jpgFile = new ScaledImageJpg(
             srcFile,
             length,
             width,
             START,
             SCALE_ONE_JPG_IMAGE,
             myJob
        );
        performTheShrienking(jpgFile);
        srcFileCopy = new File(srcFile.getAbsolutePath());
        jpegMetadata =infoImageJpegService. getImageMetadata(srcFileCopy);
        if (jpegMetadata != null) {
            jpgFile.setWidthScaled( infoImageJpegService.getWidth(jpegMetadata) );
            jpgFile.setLengthScaled( infoImageJpegService.getLength(jpegMetadata) );
        }
        return jpgFile;
    }

    private ScaledImageJpg performTheShrienking(ScaledImageJpg jpgFile){
        final String srcPath = jpgFile.getFilepath();
        final String tmpFilePath = srcPath + "_bak.jpg";
        int targetScale = properties.getImageTargetScale();
        int resizeFactorAsPercent = jpgFile.getScaleFactorAsPercent(targetScale);
        String command = "magick convert "
            + srcPath
            + " -resize "
            + resizeFactorAsPercent
            + "% -density 72x72 "
            + tmpFilePath;
        jpgFile.setCommand(command);
        logger.debug(command);
        if (properties.getDryRun()) {
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

    @Override
    public String getJobName() {
        return JOB_IMAGES_RESIZE.getHumanReadable();
    }

    private Log logger = LogFactory.getLog(JobImagesResizeJpgServiceImpl.class);
}
