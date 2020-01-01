package org.woehlke.tools.filenames;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.woehlke.tools.config.properties.ApplicationProperties;
import org.woehlke.tools.model.services.JobService;
import org.woehlke.tools.model.services.LogbuchServiceAsync;
import org.woehlke.tools.model.services.RenamedOneDirectoryServiceAsync;
import org.woehlke.tools.model.services.RenamedOneFileServiceAsync;
import org.woehlke.tools.model.mq.RenameQueue;
import org.woehlke.tools.jobs.traverse.TraverseDirs;
import org.woehlke.tools.jobs.traverse.TraverseFiles;
import org.woehlke.tools.jobs.rename.JobRenameFiles;
import org.woehlke.tools.jobs.rename.impl.JobRenameFilesImpl;

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
    private ApplicationProperties properties;

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
