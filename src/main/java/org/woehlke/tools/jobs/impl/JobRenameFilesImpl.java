package org.woehlke.tools.jobs.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.woehlke.tools.db.Renamed;
import org.woehlke.tools.jobs.mq.LogbuchQueueService;
import org.woehlke.tools.db.LogbuchService;
import org.woehlke.tools.jobs.common.FilenameTransform;
import org.woehlke.tools.jobs.mq.RenamedAsyncService;
import org.woehlke.tools.jobs.traverse.TraverseDirs;
import org.woehlke.tools.jobs.traverse.TraverseFiles;
import org.woehlke.tools.jobs.JobRenameFiles;

import java.io.File;
import java.util.Deque;

@Component
public class JobRenameFilesImpl extends Thread implements JobRenameFiles {

    private final TraverseDirs traverseDirs;
    private final TraverseFiles traverseFiles;
    private final LogbuchService logbuchService;
    private final LogbuchQueueService log;
    private final RenamedAsyncService renamedAsyncService;

    @Autowired
    public JobRenameFilesImpl(final LogbuchQueueService log,
                              final TraverseDirs traverseDirs,
                              final TraverseFiles traverseFiles,
                              final LogbuchService logbuchService, RenamedAsyncService renamedAsyncService) {
        this.log = log;
        this.traverseDirs = traverseDirs;
        this.traverseFiles = traverseFiles;
        this.logbuchService = logbuchService;
        this.renamedAsyncService = renamedAsyncService;
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
        this.renamedAsyncService.deleteAll();
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
        rename();
    }

    private void renameFiles(){
        traverseDirs.run();
        traverseFiles.run();
        rename();
    }

    private void rename() {
        Deque<File> stack =  this.traverseDirs.getResult();
        while (!stack.isEmpty()){
            File srcFile = stack.pop();
            String parentPath = srcFile.getParent();
            String oldFilename = srcFile.getName();
            String newFilename = FilenameTransform.getNewName(oldFilename);
            if(oldFilename.compareTo(newFilename)!=0){
                String newFilepath = parentPath + File.separator + newFilename;
                File targetFile = new File(newFilepath);
                String msg="rename: "+srcFile.getAbsolutePath()+" -> "+targetFile.getAbsolutePath();
                String category = "rename";
                String job = "RenameDirectoriesAndFiles";
                log.info(msg,category,job);
                Renamed p = new Renamed();
                p.setDirectory(srcFile.isDirectory());
                p.setParent(srcFile.getParent());
                p.setSource(srcFile.getName());
                p.setTarget(targetFile.getName());
                p.setDryRun(this.dryRun);
                this.renamedAsyncService.add(p);
                if(!this.dryRun){
                    srcFile.renameTo(targetFile);
                }
            }
            try {
                Thread.sleep(100);
            } catch (Exception e){

            }
        }
    }

    private void line(){
        log.info("*********************");
    }

}
