package org.woehlke.tools.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.woehlke.tools.filenames.RenameDirectoriesAndFiles;
import org.woehlke.tools.filesystem.TraverseDirs;
import org.woehlke.tools.filesystem.TraverseFiles;

import java.io.File;

@Component
public class RenameFilesAndDirs extends Thread implements Runnable {

    private String dataRootDir;

    private boolean dryRun = false;

    private final TraverseDirs traverseDirs;
    private final TraverseFiles traverseFiles;

    private LoggingCallback log;

    @Autowired
    public RenameFilesAndDirs(TraverseDirs traverseDirs, TraverseFiles traverseFiles) {
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
        RenameDirectoriesAndFiles renameDirectoriesAndFiles = new RenameDirectoriesAndFiles(traverseDirs,log);
        renameDirectoriesAndFiles.run();
    }

    private void renameFiles(){
        traverseDirs.run();
        traverseFiles.run();
        RenameDirectoriesAndFiles renameDirectoriesAndFiles = new RenameDirectoriesAndFiles(traverseFiles,log);
        renameDirectoriesAndFiles.run();
    }

    private void line(){
        log.info("*********************");
    }

}
