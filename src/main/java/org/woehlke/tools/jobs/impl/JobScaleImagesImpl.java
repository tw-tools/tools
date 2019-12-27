package org.woehlke.tools.jobs.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
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

    @Autowired
    public JobScaleImagesImpl(final LogbuchQueueService logbuchQueueService,
                              final TraverseDirs traverseDirs,
                              final TraverseFiles traverseFiles,
                              final ShrinkImages shrinkImages) {
        this.log = logbuchQueueService;
        this.traverseDirs = traverseDirs;
        this.traverseFiles = traverseFiles;
        this.shrinkImages = shrinkImages;
    }

    private String dataRootDir;

    public void setRootDirectory(File rootDirectory) { ;
        this.dataRootDir = rootDirectory.getAbsolutePath();
        traverseDirs.add(this.dataRootDir);
        traverseFiles.add(this.dataRootDir);
    }

    @Override
    public void run() {
        line();
        log.info("START: ScaleImages: "+this.dataRootDir);
        line();
        log.info("");
        this.traverseDirs.add(this.dataRootDir);
        this.traverseDirs.run();
        this.traverseFiles.add(this.dataRootDir);
        this.traverseFiles.run();
        line();
        log.info("DONE: ScaleImages: "+this.dataRootDir);
        line();
        shrinkImages.run();
    }

    private void line(){
        log.info("*********************");
    }

}
