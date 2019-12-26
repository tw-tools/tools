package org.woehlke.tools.images;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.woehlke.tools.db.service.LogbuchQueueService;
import org.woehlke.tools.filenames.RenameFilesAndDirsTest;
import org.woehlke.tools.filesystem.TraverseDirs;
import org.woehlke.tools.filesystem.TraverseFiles;
import org.woehlke.tools.images.impl.ScaleImagesImpl;

import java.io.File;

@SpringBootTest
public class ScaleImagesTest {

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
        log.info("start configuration");
        File rootDirectory = new File("~/tools");
        ScaleImages classUnderTest = new ScaleImagesImpl(
            logbuchQueueService, traverseDirs, traverseFiles, shrinkImages);
        log.info("setRootDirectory: " + rootDirectory.getAbsolutePath());
        log.info("dryRun:           " + dryRun);
        classUnderTest.setRootDirectory(rootDirectory, dryRun);
        log.info("START");
        classUnderTest.run();
        log.info("DONE");
    }

    private final boolean dryRun = true;

    private Log log = LogFactory.getLog(RenameFilesAndDirsTest.class);

}
