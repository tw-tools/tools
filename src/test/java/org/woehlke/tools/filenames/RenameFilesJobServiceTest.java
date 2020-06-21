package org.woehlke.tools.filenames;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
//import org.junit.jupiter.api.Test;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.woehlke.tools.config.properties.ApplicationProperties;
import org.woehlke.tools.jobs.gateways.ImagesInfoJobBackendGateway;
import org.woehlke.tools.jobs.rename.RenameFilesJobService;
import org.woehlke.tools.model.config.JobCase;
import org.woehlke.tools.model.entities.Job;
import org.woehlke.tools.model.services.JobService;
import org.woehlke.tools.model.services.LogbuchServiceAsync;
import org.woehlke.tools.model.services.RenamedOneDirectoryServiceAsync;
import org.woehlke.tools.model.services.RenamedOneFileServiceAsync;

import org.woehlke.tools.jobs.traverse.TraverseDirsService;
import org.woehlke.tools.jobs.traverse.TraverseFilesService;
import org.woehlke.tools.jobs.rename.impl.RenameFilesJobServiceImpl;

import java.io.File;

@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.NONE)
public class RenameFilesJobServiceTest {

    @Autowired
    private ImagesInfoJobBackendGateway imagesInfoJobBackendGateway;

    @Autowired
    private TraverseDirsService traverseDirsService;

    @Autowired
    private TraverseFilesService traverseFilesService;

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
        RenameFilesJobService classUnderTest = new RenameFilesJobServiceImpl(
            //imagesInfoJobBackendGateway,
            traverseDirsService,
            traverseFilesService,
            jobService,
            //renamedOneDirectoryServiceAsync,
            renamedOneFileServiceAsync,
            properties,
            logbuchServiceAsync
        );
        logger.warn("setRootDirectory: " + rootDirectory.getAbsolutePath());
        logger.info("dryRun:           " + properties.getDryRun());
        boolean dryRun=true; boolean dbActive=true;
        Job job = Job.create(JobCase.JOB_RENAME_DIRECTORIES,rootDirectory,dryRun,dbActive);
        classUnderTest.setRootDirectory(job);
        logger.warn("START");
        classUnderTest.run();
        logger.warn("DONE");
    }

    private Log logger = LogFactory.getLog(RenameFilesJobServiceTest.class);
}
