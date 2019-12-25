package org.woehlke.tools;

import org.woehlke.tools.filenames.RenameDirectoriesAndFiles;
import org.woehlke.tools.filesystem.TraverseDirs;
import org.woehlke.tools.filesystem.TraverseFiles;

import java.util.logging.Logger;

public class RenameFilesAndDirs implements Runnable {

    private final String dataRootDir;

    private final boolean dryRun = false;

    private final TraverseDirs traverseDirs;
    private final TraverseFiles traverseFiles;

    private static final Logger log = Logger.getLogger(RenameFilesAndDirs.class.getName());

    public RenameFilesAndDirs(String args[]) {
        this.dataRootDir = "/Users/tw/tools";
        traverseDirs = new TraverseDirs(this.dataRootDir,this.dryRun);
        traverseFiles = new TraverseFiles(this.dataRootDir,this.dryRun);
    }

    @Override
    public void run() {
        line();
        log.info("RenameFilesAndDirs: "+this.dataRootDir);
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
