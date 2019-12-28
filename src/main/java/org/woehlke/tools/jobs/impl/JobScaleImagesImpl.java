package org.woehlke.tools.jobs.impl;


import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.woehlke.tools.config.ToolsApplicationProperties;
import org.woehlke.tools.db.ImageJpg;
import org.woehlke.tools.db.Job;
import org.woehlke.tools.db.common.JobCase;
import org.woehlke.tools.db.services.ImageJpgService;
import org.woehlke.tools.db.services.JobService;
import org.woehlke.tools.jobs.common.FileFilterImages;
import org.woehlke.tools.jobs.images.ShrinkJpgImage;
import org.woehlke.tools.jobs.common.LogbuchQueueService;
import org.woehlke.tools.jobs.traverse.TraverseDirs;
import org.woehlke.tools.jobs.traverse.TraverseFiles;
import org.woehlke.tools.jobs.JobScaleImages;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.Deque;

@Component
public class JobScaleImagesImpl  extends Thread implements JobScaleImages {

    private final LogbuchQueueService log;
    private final TraverseDirs traverseDirs;
    private final TraverseFiles traverseFiles;
    private final JobService jobService;
    private final ShrinkJpgImage shrinkJpgImage;
    private final ImageJpgService imageJpgService;
    private final ToolsApplicationProperties toolsApplicationProperties;

    @Autowired
    public JobScaleImagesImpl(@Qualifier("jobScaleImagesQueueImpl") final LogbuchQueueService log,
                              final TraverseDirs traverseDirs,
                              final TraverseFiles traverseFiles,
                              final JobService jobService,
                              final ShrinkJpgImage shrinkJpgImage, ImageJpgService imageJpgService, ToolsApplicationProperties toolsApplicationProperties) {
        this.log = log;
        this.traverseDirs = traverseDirs;
        this.traverseFiles = traverseFiles;
        this.jobService = jobService;
        this.shrinkJpgImage = shrinkJpgImage;
        this.imageJpgService = imageJpgService;
        this.toolsApplicationProperties = toolsApplicationProperties;
    }

    private void line(){
        log.info("*********************");
    }

    private final Tika defaultTika = new Tika();
    private String dataRootDir;
    private boolean dryRun=true;
    private boolean dbActive=true;

    public void setRootDirectory(File rootDirectory) { ;
        this.dataRootDir = rootDirectory.getAbsolutePath();
        FileFilter fileFilter = new FileFilterImages();
        traverseDirs.add(this.dataRootDir, this.log, fileFilter);
        traverseFiles.add(this.dataRootDir, this.log, fileFilter);
    }

    @Override
    public void run() {
        Job myJob = Job.create(JobCase.SCALE_IMAGES,this.dataRootDir,this.dryRun,this.dbActive);
        myJob = jobService.start(myJob);
        line();
        log.info("START: ScaleImages: "+this.dataRootDir);
        line();
        log.info("");
        this.traverseDirs.run();
        this.traverseFiles.run();
        line();
        log.info("DONE: ScaleImages (traverseFiles) : "+this.dataRootDir);
        line();
        run2(myJob);
        line();
        log.info("DONE: ScaleImages: "+this.dataRootDir);
        line();
        myJob = jobService.finish(myJob);
    }

     private void run2(Job myJob) {
        Deque<File> stack =  this.traverseFiles.getResult();
        while (!stack.isEmpty()){
            File srcFile = stack.pop();
            String fileType = "unknown";
            try {
                fileType = defaultTika.detect(srcFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(fileType.compareTo("image/jpeg")==0){
                File targetFile;
                if(dryRun){
                    log.info("fileType: "+ fileType + " DryRun  shrinkJpgImage: "+srcFile.getAbsolutePath());
                    targetFile=srcFile;
                } else {
                    log.info("fileType: "+ fileType + " Perform shrinkJpgImage: "+srcFile.getAbsolutePath());
                    targetFile = shrinkJpgImage.shrienk(srcFile);
                }
                if(this.dbActive){
                    long length = 0L;
                    long width = 0L;
                    ImageJpg img = ImageJpg.create(targetFile, length, width);
                    img.setJob(myJob);
                    imageJpgService.add(img);
                }
            } else {
                log.info("fileType: "+fileType+" - "+srcFile.getAbsolutePath());
            }
        }
    }
}
