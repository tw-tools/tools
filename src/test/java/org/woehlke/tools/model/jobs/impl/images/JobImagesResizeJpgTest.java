package org.woehlke.tools.model.jobs.impl.images;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.woehlke.tools.config.properties.ApplicationProperties;
import org.woehlke.tools.config.properties.MmiProperties;
import org.woehlke.tools.model.services.JobService;
import org.woehlke.tools.filenames.JobRenameFilesTest;
import org.woehlke.tools.model.services.LogbuchServiceAsync;
import org.woehlke.tools.model.services.ScaledImageJpgServiceAsync;
import org.woehlke.tools.model.mq.LogbuchQueue;
import org.woehlke.tools.model.mq.ImagesResizeQueue;
import org.woehlke.tools.jobs.images.resize.JobImagesResizeJpg;
import org.woehlke.tools.jobs.traverse.TraverseDirs;
import org.woehlke.tools.jobs.traverse.TraverseFiles;
import org.woehlke.tools.jobs.images.resize.impl.JobImagesResizeJpgImpl;

import java.io.File;

@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.NONE)
public class JobImagesResizeJpgTest {

    @Autowired
    @Qualifier("jobImagesResizeBackendGatewayImpl")
    private LogbuchQueue logbuchQueue;

    @Autowired
    private TraverseDirs traverseDirs;

    @Autowired
    private TraverseFiles traverseFiles;

    @Autowired
    private JobService jobService;

    @Autowired
    private ImagesResizeQueue log;

    @Autowired
    private ScaledImageJpgServiceAsync scaledImageJpgServiceAsync;

    @Autowired
    private LogbuchServiceAsync logbuchServiceAsync;

    @Autowired
    private ApplicationProperties cfg;

    @Autowired
    private MmiProperties properties;

    @Test
    public void runScaleImagesTest(){
        logger.warn("start configuration");
        File rootDirectory = new File("~/tools");
        JobImagesResizeJpg classUnderTest = new JobImagesResizeJpgImpl(
            log,
            traverseDirs,
            traverseFiles,
            jobService,
            scaledImageJpgServiceAsync,
            logbuchServiceAsync,
            cfg,
            properties
        );
        logger.warn("setRootDirectory: " + rootDirectory.getAbsolutePath());
        logger.info("dryRun:           " + cfg.getDryRun());
        classUnderTest.setRootDirectory(rootDirectory);
        logger.warn("START");
        classUnderTest.run();
        logger.warn("DONE");
    }

    private Log logger = LogFactory.getLog(JobRenameFilesTest.class);

}
