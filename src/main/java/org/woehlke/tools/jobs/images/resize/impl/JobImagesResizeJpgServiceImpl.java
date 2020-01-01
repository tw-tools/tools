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
import org.springframework.stereotype.Service;
import org.woehlke.tools.config.properties.ApplicationProperties;
import org.woehlke.tools.config.properties.MmiProperties;
import org.woehlke.tools.jobs.common.impl.AbstractJobServiceImpl;
import org.woehlke.tools.model.config.JobEventType;
import org.woehlke.tools.model.entities.Logbuch;
import org.woehlke.tools.model.entities.ScaledImageJpg;
import org.woehlke.tools.model.entities.Job;
import org.woehlke.tools.model.services.JobService;
import org.woehlke.tools.model.services.LogbuchServiceAsync;
import org.woehlke.tools.model.services.ScaledImageJpgServiceAsync;
import org.woehlke.tools.jobs.images.resize.ImagesResizeJobBackendGateway;
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

    private final ImagesResizeJobBackendGateway imagesResizeJobBackendGateway;
    private final TraverseDirsService traverseDirsService;
    private final TraverseFilesService traverseFilesService;
    private final JobService jobService;
    private final ScaledImageJpgServiceAsync scaledImageJpgServiceAsync;
    private final MmiProperties mmiProperties;

    @Autowired
    public JobImagesResizeJpgServiceImpl(
        final ImagesResizeJobBackendGateway imagesResizeJobBackendGateway,
        final TraverseDirsService traverseDirsService,
        final TraverseFilesService traverseFilesService,
        final JobService jobService,
        final ScaledImageJpgServiceAsync scaledImageJpgServiceAsync,
        final LogbuchServiceAsync logbuchServiceAsync,
        final ApplicationProperties properties,
        final MmiProperties mmiProperties
    ) {
        super(logbuchServiceAsync,properties);
        this.imagesResizeJobBackendGateway = imagesResizeJobBackendGateway;
        this.traverseDirsService = traverseDirsService;
        this.traverseFilesService = traverseFilesService;
        this.jobService = jobService;
        this.scaledImageJpgServiceAsync = scaledImageJpgServiceAsync;
        this.mmiProperties = mmiProperties;
    }

    //private final Tika defaultTika = new Tika();

    public void setRootDirectory(Job job) {
        this.job=job;
        FileFilter fileFilter = new FileFilterImages();
        traverseDirsService.add( this.job, fileFilter);
        traverseFilesService.add( this.job, fileFilter);
    }

    @Override
    public String getJobName() {
        return JOB_IMAGES_RESIZE.getHumanReadable();
    }

    @Override
    public void run() {
        signalJobStartToDb();
        line();
        this.traverseDirsService.run();
        line();
        this.traverseFilesService.run();
        line();
        scaleJpgImages();
        line();
        signalJobDoneToDb();
    }



    private void signalJobStartToDb(){
        info(START, SCALE_JPG_IMAGES);
        if(this.properties.getDbActive()) {
            job = jobService.start(job);
        }
    }

    private void signalJobDoneToDb(){
        if(this.properties.getDbActive()) {
            job = jobService.finish(job);
        }
        info(DONE, SCALE_JPG_IMAGES);
    }

     private void scaleJpgImages() {
        info( START, SCALE_JPG_IMAGES);
        Deque<File> stack =  this.traverseFilesService.getResult();
        while (!stack.isEmpty()){
            File srcFile = stack.pop();
            ScaledImageJpg o = scaleOneImage(
                srcFile,
                job,
                START,
                SCALE_ONE_JPG_IMAGE);
            scaledImageJpgServiceAsync.add(o);
        }
        info( DONE,SCALE_JPG_IMAGES);
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

    private Log logger = LogFactory.getLog(JobImagesResizeJpgServiceImpl.class);
}
