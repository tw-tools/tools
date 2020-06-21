package org.woehlke.tools.model.jobs.impl.images;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.woehlke.tools.config.properties.ToolsApplicationProperties;
import org.woehlke.tools.config.properties.ToolsMmiProperties;
import org.woehlke.tools.filenames.RenameFilesJobServiceTest;
import org.woehlke.tools.jobs.gateways.ImagesResizeJobBackendGateway;
import org.woehlke.tools.jobs.images.common.InfoImageJpegService;
import org.woehlke.tools.model.config.JobCase;
import org.woehlke.tools.model.entities.Job;
import org.woehlke.tools.model.services.JobService;
import org.woehlke.tools.model.services.LogbuchServiceAsync;
import org.woehlke.tools.model.services.ScaledImageJpgServiceAsync;
import org.woehlke.tools.jobs.images.resize.JobImagesResizeJpgService;
import org.woehlke.tools.jobs.traverse.TraverseDirsService;
import org.woehlke.tools.jobs.traverse.TraverseFilesService;
import org.woehlke.tools.jobs.images.resize.impl.JobImagesResizeJpgServiceImpl;

import java.io.File;

@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.NONE)
public class JobImagesResizeJpgServiceTest {

    @Autowired
    private TraverseDirsService traverseDirsService;

    @Autowired
    private TraverseFilesService traverseFilesService;

    @Autowired
    private JobService jobService;

    @Autowired
    private ImagesResizeJobBackendGateway imagesResizeJobBackendGateway;

    @Autowired
    private InfoImageJpegService infoImageJpegService;

    @Autowired
    private ScaledImageJpgServiceAsync scaledImageJpgServiceAsync;

    @Autowired
    private LogbuchServiceAsync logbuchServiceAsync;

    @Autowired
    private ToolsApplicationProperties cfg;

    @Autowired
    private ToolsMmiProperties properties;

    @Test
    public void runScaleImagesTest(){
        logger.warn("start configuration");
        File rootDirectory = new File("~/tools");
        JobImagesResizeJpgService classUnderTest = new JobImagesResizeJpgServiceImpl(
           // imagesResizeJobBackendGateway,
            traverseDirsService,
            traverseFilesService,
            jobService,
            scaledImageJpgServiceAsync,
            logbuchServiceAsync,
            cfg,
            infoImageJpegService
        );
        logger.warn("setRootDirectory: " + rootDirectory.getAbsolutePath());
        logger.info("dryRun:           " + cfg.getDryRun());
        boolean dryRun=true; boolean dbActive=true;
        Job job = Job.create(JobCase.JOB_IMAGES_RESIZE,rootDirectory,dryRun,dbActive);
        classUnderTest.setRootDirectory(job);
        logger.warn("START");
        classUnderTest.run();
        logger.warn("DONE");
    }

    private Log logger = LogFactory.getLog(RenameFilesJobServiceTest.class);

}
