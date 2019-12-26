package org.woehlke.tools.filesystem.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.woehlke.tools.db.service.DbLogger;
import org.woehlke.tools.filesystem.RenameDirectoriesAndFiles;
import org.woehlke.tools.filesystem.TraverseDirs;
import org.woehlke.tools.filesystem.TraverseFiles;
import org.woehlke.tools.filesystem.RenameFilesAndDirs;

import java.io.File;

@Component
public class RenameFilesAndDirsImpl extends Thread implements RenameFilesAndDirs {

    private final TraverseDirs traverseDirs;
    private final TraverseFiles traverseFiles;
    private final RenameDirectoriesAndFiles renameDirectoriesAndFiles;
    private final DbLogger log;

    @Autowired
    public RenameFilesAndDirsImpl(final DbLogger log,
                              final TraverseDirs traverseDirs,
                              final TraverseFiles traverseFiles,
                              final RenameDirectoriesAndFiles renameDirectoriesAndFiles) {
        this.log = log;
        this.traverseDirs = traverseDirs;
        this.traverseFiles = traverseFiles;
        this.renameDirectoriesAndFiles = renameDirectoriesAndFiles;
    }

    private String dataRootDir;
    private boolean dryRun;

    public void setRootDirectory(File rootDirectory, boolean dryRun) { ;
        this.dataRootDir = rootDirectory.getAbsolutePath();
        this.dryRun = dryRun;
        traverseDirs.add(this.dataRootDir,this.dryRun);
        traverseFiles.add(this.dataRootDir,this.dryRun);
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
        renameDirectoriesAndFiles.run();
    }

    private void renameFiles(){
        traverseDirs.run();
        traverseFiles.run();
        renameDirectoriesAndFiles.run();
    }

    private void line(){
        log.info("*********************");
    }

}
