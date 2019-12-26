package org.woehlke.tools.filesystem.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.woehlke.tools.db.service.LogbuchQueueService;
import org.woehlke.tools.db.service.LogbuchService;
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
    private final LogbuchQueueService log;
    private final LogbuchService logbuchService;

    @Autowired
    public RenameFilesAndDirsImpl(final LogbuchQueueService log,
                                  final TraverseDirs traverseDirs,
                                  final TraverseFiles traverseFiles,
                                  final RenameDirectoriesAndFiles renameDirectoriesAndFiles, LogbuchService logbuchService) {
        this.log = log;
        this.traverseDirs = traverseDirs;
        this.traverseFiles = traverseFiles;
        this.renameDirectoriesAndFiles = renameDirectoriesAndFiles;
        this.logbuchService = logbuchService;
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
        logbuchService.deleteAll();
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
