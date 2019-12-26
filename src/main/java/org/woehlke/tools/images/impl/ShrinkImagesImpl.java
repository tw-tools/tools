package org.woehlke.tools.images.impl;

import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.woehlke.tools.db.service.DbLogger;
//import org.woehlke.tools.filesystem.TraverseDirs;
import org.woehlke.tools.filesystem.TraverseFiles;
import org.woehlke.tools.images.ShrinkImages;
import org.woehlke.tools.images.ShrinkJpgImage;

import java.io.File;
import java.io.IOException;
import java.util.Deque;

@Component
public class ShrinkImagesImpl implements ShrinkImages {

    private final Tika defaultTika = new Tika();

    private final ShrinkJpgImage shrinkJpgImage;
    //private final TraverseDirs traverseDirs;
    private final TraverseFiles traverseFiles;
    private final DbLogger log;

    @Autowired
    public ShrinkImagesImpl(final ShrinkJpgImage shrinkJpgImage,
                        //final TraverseDirs traverseDirs,
                        final TraverseFiles traverseFiles,
                        final DbLogger log) {
        this.shrinkJpgImage = shrinkJpgImage;
        //this.traverseDirs = traverseDirs;
        this.traverseFiles = traverseFiles;
        this.log = log;
    }

    @Override
    public void run() {
        Deque<File> stack =  this.traverseFiles.getResult();
        while (!stack.isEmpty()){
            File srcFile = stack.pop();
            String fileType = "unknown";
            try {
                fileType = defaultTika.detect(srcFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            log.info("fileType: "+fileType);
            if(fileType.compareTo("image/jpeg")==0){
                if( this.traverseFiles.isDryRun()){
                    log.info("DryRun  shrinkJpgImage: "+srcFile.getAbsolutePath());
                } else {
                    log.info("Perform shrinkJpgImage: "+srcFile.getAbsolutePath());
                    File targetFile = shrinkJpgImage.shrienk(srcFile);
                }
            }
        }
    }
}