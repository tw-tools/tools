package org.woehlke.tools.jobs.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.woehlke.tools.db.*;
import org.woehlke.tools.db.common.JobCase;
import org.woehlke.tools.db.services.JobService;
import org.woehlke.tools.db.services.LogbuchService;
import org.woehlke.tools.jobs.mq.LogbuchQueueService;
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
    private final JobService jobService;

    @Autowired
    public JobRenameFilesImpl(final LogbuchQueueService log,
                              final TraverseDirs traverseDirs,
                              final TraverseFiles traverseFiles,
                              final LogbuchService logbuchService, RenamedAsyncService renamedAsyncService, JobService jobService) {
        this.log = log;
        this.traverseDirs = traverseDirs;
        this.traverseFiles = traverseFiles;
        this.logbuchService = logbuchService;
        this.renamedAsyncService = renamedAsyncService;
        this.jobService = jobService;
    }

    private String dataRootDir;
    private boolean dryRun=true;
    private boolean dbActive=true;

    public void setRootDirectory(File rootDirectory) { ;
        this.dataRootDir = rootDirectory.getAbsolutePath();
        traverseDirs.add(this.dataRootDir);
        traverseFiles.add(this.dataRootDir);
    }

    @Override
    public void run() {
        Job myJob = Job.create(JobCase.RENAME_FILES,this.dataRootDir,this.dryRun,this.dbActive);
        myJob = jobService.start(myJob);
        line();
        log.info("START: RenameFilesAndDirs: "+this.dataRootDir);
        line();
        log.info("");
        renameDirectories(  myJob);
        line();
        renameFiles(  myJob);
        log.info("DONE: RenameFilesAndDirs: "+this.dataRootDir);
        line();
        jobService.finish(myJob);
    }

    private void renameDirectories(Job myJob){
        traverseDirs.run();
        rename(myJob);
    }

    private void renameFiles(Job myJob){
        traverseDirs.run();
        traverseFiles.run();
        rename(myJob);
    }

    private void rename(Job myJob) {
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
                if(dbActive){
                    Renamed p = new Renamed();
                    p.setJob(myJob);
                    p.setDirectory(srcFile.isDirectory());
                    p.setParent(srcFile.getParent());
                    p.setSource(srcFile.getName());
                    p.setTarget(targetFile.getName());
                    p.setDryRun(this.dryRun);
                    this.renamedAsyncService.add(p);
                }
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
