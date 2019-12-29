package org.woehlke.tools.filenames;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.woehlke.tools.config.ToolsApplicationProperties;
import org.woehlke.tools.model.db.services.JobEventService;
import org.woehlke.tools.model.db.services.JobService;
import org.woehlke.tools.jobs.common.JobEventMessages;
import org.woehlke.tools.jobs.common.mq.JobEventServiceAsyncService;
import org.woehlke.tools.jobs.common.mq.LogbuchQueueService;
import org.woehlke.tools.model.traverse.TraverseDirs;
import org.woehlke.tools.model.traverse.TraverseFiles;
import org.woehlke.tools.view.jobs.JobRename;
import org.woehlke.tools.view.jobs.impl.JobRenameImpl;

import java.io.File;

@SpringBootTest
public class JobRenameTest {

    @Autowired
    @Qualifier("jobRenameFilesQueueImpl")
   private LogbuchQueueService log;

    @Autowired
   private TraverseDirs traverseDirs;

    @Autowired
   private TraverseFiles traverseFiles;

    @Autowired
   private JobEventServiceAsyncService jobRenameFilesAsyncService;

    @Autowired
   private JobService jobService;

    @Autowired
   private JobEventService jobEventService;

   @Autowired
   private ToolsApplicationProperties properties;

   @Autowired
   private JobEventMessages msg;

    private boolean dryRun;
    private boolean dbActive;

    @Test
    public void runRenameFilesAndDirsTest(){
        logger.warn("configuration");
        File rootDirectory = new File("~/tools");
        JobRename classUnderTest = new JobRenameImpl(
            log,
            traverseDirs,
            traverseFiles,
            jobRenameFilesAsyncService,
             jobService,
             jobEventService,
             properties,
             msg
        );
        logger.warn("setRootDirectory: " + rootDirectory.getAbsolutePath());
        logger.info("dryRun:           " + dryRun);
        classUnderTest.setRootDirectory(rootDirectory);
        logger.warn("START");
        classUnderTest.start();
        logger.warn("DONE");
    }

    private Log logger = LogFactory.getLog(JobRenameTest.class);
}
