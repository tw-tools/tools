package org.woehlke.tools;


import org.woehlke.tools.filesystem.TraverseDirs;
import org.woehlke.tools.filesystem.TraverseFiles;
import org.woehlke.tools.images.ShrinkImages;
import org.woehlke.tools.db.service.DbLogger;
import org.woehlke.tools.view.LoggingCallback;

import java.io.File;

public class ScaleImages implements Runnable ,  LoggingCallback{


    private final DbLogger dbLogger;

    private String dataRootDir;

    private boolean dryRun = false;

    private final TraverseDirs traverseDirs;
    private final TraverseFiles traverseFiles;

    private LoggingCallback log;


    public ScaleImages(DbLogger dbLogger, TraverseDirs traverseDirs, TraverseFiles traverseFiles) {
        this.dbLogger = dbLogger;
        this.traverseDirs = traverseDirs;
        this.traverseFiles = traverseFiles;
    }

    public void setRootDirectory(File rootDirectory, boolean dryRun,LoggingCallback log) { ;
        this.dataRootDir = rootDirectory.getAbsolutePath();
        this.dryRun = dryRun;
        this.log = log;
        traverseDirs.add(this.dataRootDir,this.dryRun,log);
        traverseFiles.add(this.dataRootDir,this.dryRun,log);
    }


    @Override
    public void run() {
        line();
        log.info("ScaleImages: "+this.dataRootDir);
        line();
        log.info("");
        this.traverseDirs.add(this.dataRootDir, this.dryRun, this);
        this.traverseDirs.run();
        this.traverseFiles.add(this.dataRootDir, this.dryRun, this);
        traverseFiles.run();
        line();
        log.info("fertig: ScaleImages: "+this.dataRootDir);
        line();
        ShrinkImages shrinkImages = new ShrinkImages(traverseFiles);
        shrinkImages.run();
    }

    private void line(){
        log.info("*********************");
    }

    @Override
    public void info(String msg) {
        dbLogger.info(msg);
    }
}
