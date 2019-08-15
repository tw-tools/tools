package org.woehlke.tools.images;

import org.woehlke.tools.filesystem.TraverseDirs;
import org.woehlke.tools.filesystem.TraverseFiles;

public class ScaleImages implements Runnable {

    private final String dataRootDir;

    public ScaleImages(String args[]) {
        this.dataRootDir = args[0];
    }

    @Override
    public void run() {
        line();
        System.out.print("ScaleImages: "+this.dataRootDir);
        line();
        System.out.println();
        TraverseDirs runner = new TraverseDirs(this.dataRootDir);
        runner.run();
        TraverseFiles traverseFiles = new TraverseFiles(this.dataRootDir);
        traverseFiles.run();
        line();
        System.out.println("fertig: ScaleImages: "+this.dataRootDir);
        line();
    }

    private void line(){
        System.out.println("*********************");
    }

    public static void main(String args[]){
        ScaleImages app = new ScaleImages(args);
        app.run();
    }
}
