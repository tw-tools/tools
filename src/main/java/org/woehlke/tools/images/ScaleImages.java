package org.woehlke.tools.images;


import org.woehlke.tools.filesystem.TraverseDirs;
import org.woehlke.tools.filesystem.TraverseFiles;
import org.woehlke.tools.images.impl.ShrinkJpgImageImpl;

import java.util.logging.Logger;

public class ScaleImages implements Runnable {

    private final String dataRootDir;

    private final boolean dryRun = true;

    public ScaleImages(String args[]) {
        this.dataRootDir = args[0];
    }

    @Override
    public void run() {
        line();
        System.out.print("ScaleImages: "+this.dataRootDir);
        line();
        log.info("");
        TraverseDirs runner = new TraverseDirs(this.dataRootDir, this.dryRun);
        runner.run();
        TraverseFiles traverseFiles = new TraverseFiles(this.dataRootDir, this.dryRun);
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

    private static final Logger log = Logger.getLogger(ScaleImages.class.getName());

    public static void main(String args[]){
        ScaleImages app = new ScaleImages(args);
        app.run();
    }
}
