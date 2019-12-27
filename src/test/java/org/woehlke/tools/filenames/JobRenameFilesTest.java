package org.woehlke.tools.filenames;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.woehlke.tools.db.services.JobService;
import org.woehlke.tools.jobs.mq.JobRenameFilesAsyncService;
import org.woehlke.tools.jobs.common.LogbuchQueueService;
import org.woehlke.tools.db.services.LogbuchService;
import org.woehlke.tools.jobs.traverse.TraverseDirs;
import org.woehlke.tools.jobs.traverse.TraverseFiles;
import org.woehlke.tools.jobs.JobRenameFiles;
import org.woehlke.tools.jobs.impl.JobRenameFilesImpl;

import java.io.File;

@SpringBootTest
public class JobRenameFilesTest {

    @Autowired
    private LogbuchService logbuchService;

    @Autowired
    @Qualifier("jobRenameFilesQueueImpl")
    private LogbuchQueueService logbuchQueueService;

    @Autowired
    private TraverseDirs traverseDirs;

    @Autowired
    private TraverseFiles traverseFiles;

    @Autowired
    private JobRenameFilesAsyncService renamedAsyncService;

    @Autowired
    private JobService jobService;

    @Test
    public void runRenameFilesAndDirsTest(){
        log.warn("configuration");
        File rootDirectory = new File("~/tools");
        JobRenameFiles classUnderTest = new JobRenameFilesImpl(
            logbuchQueueService,
            traverseDirs,
            traverseFiles,
            logbuchService,
            renamedAsyncService,
            jobService);
        log.warn("setRootDirectory: " + rootDirectory.getAbsolutePath());
        log.info("dryRun:           " + dryRun);
        classUnderTest.setRootDirectory(rootDirectory);
        log.warn("START");
        classUnderTest.start();
        log.warn("DONE");
    }

    private final boolean dryRun = true;

    private Log log = LogFactory.getLog(JobRenameFilesTest.class);
}
