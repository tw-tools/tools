package org.woehlke.tools.jobs.images;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.woehlke.tools.jobs.mq.LogbuchQueueService;
import org.woehlke.tools.filenames.JobRenameFilesTest;
import org.woehlke.tools.jobs.JobScaleImages;
import org.woehlke.tools.jobs.traverse.TraverseDirs;
import org.woehlke.tools.jobs.traverse.TraverseFiles;
import org.woehlke.tools.jobs.impl.JobScaleImagesImpl;

import java.io.File;

@SpringBootTest
public class JobScaleImagesTest {

    @Autowired
    private LogbuchQueueService logbuchQueueService;

    @Autowired
    private TraverseDirs traverseDirs;

    @Autowired
    private TraverseFiles traverseFiles;

    @Autowired
    private ShrinkImages shrinkImages;

    @Test
    public void runScaleImagesTest(){
        log.warn("start configuration");
        File rootDirectory = new File("~/tools");
        JobScaleImages classUnderTest = new JobScaleImagesImpl(
            logbuchQueueService, traverseDirs, traverseFiles, shrinkImages, jobService);
        log.warn("setRootDirectory: " + rootDirectory.getAbsolutePath());
        log.info("dryRun:           " + dryRun);
        classUnderTest.setRootDirectory(rootDirectory, dryRun);
        log.warn("START");
        classUnderTest.run();
        log.warn("DONE");
    }

    private final boolean dryRun = true;

    private Log log = LogFactory.getLog(JobRenameFilesTest.class);

}
