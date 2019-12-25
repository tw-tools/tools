package org.woehlke.tools.view;

import org.springframework.stereotype.Component;
import org.woehlke.tools.filenames.RenameDirectoriesAndFiles;
import org.woehlke.tools.filesystem.TraverseDirs;
import org.woehlke.tools.filesystem.TraverseFiles;

import java.io.File;

@Component
public class RenameFilesAndDirs implements Runnable {

    private String dataRootDir;

    private boolean dryRun = false;

    private TraverseDirs traverseDirs;
    private TraverseFiles traverseFiles;

    private LoggingCallback log;

    public RenameFilesAndDirs() {
    }

    public void setRootDirectory(File rootDirectory,boolean dryRun,LoggingCallback log) { ;
        this.dataRootDir = rootDirectory.getAbsolutePath();
        this.dryRun = dryRun;
        this.log = log;
        traverseDirs = new TraverseDirs(this.dataRootDir,this.dryRun);
        traverseFiles = new TraverseFiles(this.dataRootDir,this.dryRun);
    }

    @Override
    public void run() {
        line();
        log.info("START: RenameFilesAndDirs: "+this.dataRootDir);
        line();
        log.info("");
        renameDirectories();
        line();
        renameFiles();
        log.info("DONE: RenameFilesAndDirs: "+this.dataRootDir);
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

}
