package org.woehlke.tools.filenames;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.woehlke.tools.config.properties.ToolsApplicationProperties;
import org.woehlke.tools.model.db.services.JobService;
import org.woehlke.tools.model.db.services.LogbuchServiceAsync;
import org.woehlke.tools.model.db.services.RenamedOneDirectoryServiceAsync;
import org.woehlke.tools.model.db.services.RenamedOneFileServiceAsync;
import org.woehlke.tools.model.mq.RenameQueue;
import org.woehlke.tools.model.traverse.TraverseDirs;
import org.woehlke.tools.model.traverse.TraverseFiles;
import org.woehlke.tools.model.jobs.JobRenameFiles;
import org.woehlke.tools.model.jobs.impl.JobRenameFilesImpl;

import java.io.File;

@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.NONE)
public class JobRenameFilesTest {

    @Autowired
    private RenameQueue log;

    @Autowired
    private TraverseDirs traverseDirs;

    @Autowired
    private TraverseFiles traverseFiles;

    @Autowired
    private JobService jobService;

    @Autowired
    private ToolsApplicationProperties properties;

    @Autowired
    private RenamedOneDirectoryServiceAsync renamedOneDirectoryServiceAsync;

    @Autowired
    private RenamedOneFileServiceAsync renamedOneFileServiceAsync;

    @Autowired
    private LogbuchServiceAsync logbuchServiceAsync;

    @Test
    public void runRenameFilesAndDirsTest(){
        logger.warn("configuration");
        File rootDirectory = new File("~/tools");
        JobRenameFiles classUnderTest = new JobRenameFilesImpl(
            log,
            jobRenameFilesQueue, traverseDirs,
            traverseFiles,
            jobService,
            renamedOneDirectoryServiceAsync,
            renamedOneFileServiceAsync,
            properties,
            logbuchServiceAsync
        );
        logger.warn("setRootDirectory: " + rootDirectory.getAbsolutePath());
        logger.info("dryRun:           " + properties.getDryRun());
        classUnderTest.setRootDirectory(rootDirectory);
        logger.warn("START");
        classUnderTest.start();
        logger.warn("DONE");
    }

    private Log logger = LogFactory.getLog(JobRenameFilesTest.class);
}
