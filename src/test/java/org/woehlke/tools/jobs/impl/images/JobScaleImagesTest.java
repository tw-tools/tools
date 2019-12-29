package org.woehlke.tools.jobs.impl.images;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.woehlke.tools.config.ToolsApplicationProperties;
import org.woehlke.tools.model.db.services.ImageJpgService;
import org.woehlke.tools.model.db.services.JobEventService;
import org.woehlke.tools.model.db.services.JobService;
import org.woehlke.tools.filenames.JobRenameTest;
import org.woehlke.tools.jobs.common.mq.LogbuchQueueService;
import org.woehlke.tools.jobs.images.ShrinkJpgImage;
import org.woehlke.tools.view.jobs.JobScaleImages;
import org.woehlke.tools.model.traverse.TraverseDirs;
import org.woehlke.tools.model.traverse.TraverseFiles;
import org.woehlke.tools.view.jobs.impl.JobScaleImagesImpl;

import java.io.File;

@SpringBootTest
public class JobScaleImagesTest {

    @Autowired
    @Qualifier("jobScaleImagesQueueImpl")
    private LogbuchQueueService logbuchQueueService;

    @Autowired
    private TraverseDirs traverseDirs;

    @Autowired
    private TraverseFiles traverseFiles;

    @Autowired
    private ShrinkJpgImage shrinkJpgImage;

    @Autowired
    private JobService jobService;

    @Autowired
    private JobEventService jobEventService;

    @Autowired
    private ImageJpgService imageJpgService;

    @Autowired
    private ToolsApplicationProperties properties;

    @Test
    public void runScaleImagesTest(){
        logger.warn("start configuration");
        File rootDirectory = new File("~/tools");
        JobScaleImages classUnderTest = new JobScaleImagesImpl(
            logbuchQueueService,
            traverseDirs,
            traverseFiles,
            jobService,
            jobEventService,
            shrinkJpgImage,
            imageJpgService,
            properties
        );
        logger.warn("setRootDirectory: " + rootDirectory.getAbsolutePath());
        logger.info("dryRun:           " + dryRun);
        classUnderTest.setRootDirectory(rootDirectory);
        logger.warn("START");
        classUnderTest.run();
        logger.warn("DONE");
    }

    private boolean dryRun = true;
    private boolean dbActive = true;

    private Log logger = LogFactory.getLog(JobRenameTest.class);

}
