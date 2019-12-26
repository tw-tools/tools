package org.woehlke.tools;


import org.woehlke.tools.filesystem.TraverseDirs;
import org.woehlke.tools.filesystem.TraverseFiles;
import org.woehlke.tools.images.ShrinkImages;
import org.woehlke.tools.db.service.DbLogger;

import java.io.File;

public class ScaleImages implements Runnable {

    private final DbLogger log;
    private final TraverseDirs traverseDirs;
    private final TraverseFiles traverseFiles;
    private final ShrinkImages shrinkImages;

    public ScaleImages(final DbLogger dbLogger,
                       final TraverseDirs traverseDirs,
                       final TraverseFiles traverseFiles,
                       final ShrinkImages shrinkImages) {
        this.log = dbLogger;
        this.traverseDirs = traverseDirs;
        this.traverseFiles = traverseFiles;
        this.shrinkImages = shrinkImages;
    }

    private String dataRootDir;
    private boolean dryRun = false;

    public void setRootDirectory(File rootDirectory, boolean dryRun) { ;
        this.dataRootDir = rootDirectory.getAbsolutePath();
        this.dryRun = dryRun;
        traverseDirs.add(this.dataRootDir,this.dryRun);
        traverseFiles.add(this.dataRootDir,this.dryRun);
    }

    @Override
    public void run() {
        line();
        log.info("START: ScaleImages: "+this.dataRootDir);
        line();
        log.info("");
        this.traverseDirs.add(this.dataRootDir, this.dryRun);
        this.traverseDirs.run();
        this.traverseFiles.add(this.dataRootDir, this.dryRun);
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
