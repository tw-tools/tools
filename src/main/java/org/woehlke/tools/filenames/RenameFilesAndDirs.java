package org.woehlke.tools.filenames;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.woehlke.tools.filesystem.TraverseDirs;
import org.woehlke.tools.filesystem.TraverseFiles;

public class RenameFilesAndDirs implements Runnable {

    private final String dataRootDir;

    private final boolean dryRun = false;

    private final TraverseDirs traverseDirs;
    private final TraverseFiles traverseFiles;

    private static final Logger log = LoggerFactory.getLogger(RenameFilesAndDirs.class);

    public RenameFilesAndDirs(String args[]) {
        this.dataRootDir = args[0];
        traverseDirs = new TraverseDirs(this.dataRootDir,this.dryRun);
        traverseFiles = new TraverseFiles(this.dataRootDir,this.dryRun);
    }

    @Override
    public void run() {
        line();
        System.out.print("RenameFilesAndDirs: "+this.dataRootDir);
        line();
        log.info("");
        renameDirectories();
        line();
        renameFiles();
        log.info("fertig: RenameFilesAndDirs: "+this.dataRootDir);
        line();
    }

    private void renameDirectories(){
        traverseDirs.run();
        RenameDirectoriesAndFiles renameDirectoriesAndFiles = new RenameDirectoriesAndFiles(traverseDirs);
        renameDirectoriesAndFiles.run();
    }

    private void renameFiles(){
        traverseDirs.run();
        traverseFiles.run();
        RenameDirectoriesAndFiles renameDirectoriesAndFiles = new RenameDirectoriesAndFiles(traverseFiles);
        renameDirectoriesAndFiles.run();
    }

    private void line(){
        log.info("*********************");
    }

    public static void main(String args[]){
        RenameFilesAndDirs app = new RenameFilesAndDirs(args);
        app.run();
    }
}
