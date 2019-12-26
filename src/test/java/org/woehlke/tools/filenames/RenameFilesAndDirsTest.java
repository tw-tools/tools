package org.woehlke.tools.filenames;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.woehlke.tools.db.service.LogbuchQueueService;
import org.woehlke.tools.db.service.LogbuchService;
import org.woehlke.tools.filesystem.RenameDirectoriesAndFiles;
import org.woehlke.tools.filesystem.TraverseDirs;
import org.woehlke.tools.filesystem.TraverseFiles;
import org.woehlke.tools.filesystem.RenameFilesAndDirs;
import org.woehlke.tools.filesystem.impl.RenameFilesAndDirsImpl;

import java.io.File;

@SpringBootTest
public class RenameFilesAndDirsTest {

    @Autowired
    private LogbuchService logbuchService;

    @Autowired
    private LogbuchQueueService logbuchQueueService;

    @Autowired
    private TraverseDirs traverseDirs;

    @Autowired
    private TraverseFiles traverseFiles;

    @Autowired
    private  RenameDirectoriesAndFiles renameDirectoriesAndFiles;

    @Test
    public void runRenameFilesAndDirsTest(){
        log.info("configuration");
        File rootDirectory = new File("~/tools");
        RenameFilesAndDirs classUnderTest = new RenameFilesAndDirsImpl(
            logbuchQueueService,
            traverseDirs, traverseFiles,
            renameDirectoriesAndFiles,
            logbuchService);
        log.info("setRootDirectory: " + rootDirectory.getAbsolutePath());
        log.info("dryRun:           " + dryRun);
        classUnderTest.setRootDirectory(rootDirectory, dryRun);
        log.info("START");
        classUnderTest.start();
        log.info("DONE");
    }

    private final boolean dryRun = true;

    private Log log = LogFactory.getLog(RenameFilesAndDirsTest.class);
}
