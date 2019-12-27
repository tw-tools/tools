package org.woehlke.tools.jobs.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.woehlke.tools.db.Job;
import org.woehlke.tools.db.common.JobCase;
import org.woehlke.tools.db.services.JobService;
import org.woehlke.tools.jobs.traverse.TraverseDirs;
import org.woehlke.tools.jobs.traverse.TraverseFiles;
import org.woehlke.tools.jobs.JobScaleImages;
import org.woehlke.tools.jobs.images.ShrinkImages;
import org.woehlke.tools.jobs.mq.LogbuchQueueService;

import java.io.File;

@Component
public class JobScaleImagesImpl implements JobScaleImages {

    private final LogbuchQueueService log;
    private final TraverseDirs traverseDirs;
    private final TraverseFiles traverseFiles;
    private final ShrinkImages shrinkImages;
    private final JobService jobService;

    @Autowired
    public JobScaleImagesImpl(final LogbuchQueueService logbuchQueueService,
                              final TraverseDirs traverseDirs,
                              final TraverseFiles traverseFiles,
                              final ShrinkImages shrinkImages, JobService jobService) {
        this.log = logbuchQueueService;
        this.traverseDirs = traverseDirs;
        this.traverseFiles = traverseFiles;
        this.shrinkImages = shrinkImages;
        this.jobService = jobService;
    }

    private String dataRootDir;
    private boolean dryRun=true;
    private boolean dbActive=true;

    public void setRootDirectory(File rootDirectory) { ;
        this.dataRootDir = rootDirectory.getAbsolutePath();
        traverseDirs.add(this.dataRootDir);
        traverseFiles.add(this.dataRootDir);
    }

    @Override
    public void run() {
        Job myJob = Job.create(JobCase.SCALE_IMAGES,this.dataRootDir,this.dryRun,this.dbActive);
        myJob = jobService.start(myJob);
        line();
        log.info("START: ScaleImages: "+this.dataRootDir);
        line();
        log.info("");
        this.traverseDirs.run();
        this.traverseFiles.run();
        line();
        log.info("DONE: ScaleImages: "+this.dataRootDir);
        line();
        shrinkImages.run();
        myJob = jobService.finish(myJob);
    }

    private void line(){
        log.info("*********************");
    }

}
