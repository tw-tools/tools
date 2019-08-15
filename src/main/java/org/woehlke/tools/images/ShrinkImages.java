package org.woehlke.tools.images;

import org.apache.tika.Tika;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.woehlke.tools.filenames.FilenameTransform;
import org.woehlke.tools.filesystem.Traverse;
import org.woehlke.tools.images.impl.ShrinkJpgImageImpl;

import java.io.File;
import java.io.IOException;
import java.util.Deque;

public class ShrinkImages implements Runnable {

    private final Traverse runner;
    private final Tika defaultTika = new Tika();
    private final ShrinkJpgImage shrinkJpgImage = new ShrinkJpgImageImpl();

    private static final Logger log = LoggerFactory.getLogger(ShrinkImages.class);

    public ShrinkImages(Traverse runner) {
        this.runner = runner;
    }

    @Override
    public void run() {
        Deque<File> stack =  this.runner.getResult();
        while (!stack.isEmpty()){
            File srcFile = stack.pop();
            String fileType = "unknown";
            try {
                fileType = defaultTika.detect(srcFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(fileType.compareTo("image/jpeg")==0){
                File targetFile = shrinkJpgImage.shrienk(srcFile);
            }
        }
    }
}
