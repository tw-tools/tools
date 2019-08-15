package org.woehlke.tools.images;

import org.apache.tika.Tika;
import org.woehlke.tools.filesystem.Traverse;
import org.woehlke.tools.images.impl.ShrinkJpgImageImpl;
import org.woehlke.tools.images.model.JpgImage;

import java.io.File;
import java.io.IOException;
import java.util.Deque;
import java.util.logging.Logger;

public class ShrinkImages implements Runnable {

    private final Traverse runner;
    private final Tika defaultTika = new Tika();
    private final ShrinkJpgImage shrinkJpgImage = new ShrinkJpgImageImpl();

    private static final Logger log = Logger.getLogger(ShrinkImages.class.getName());

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
        for(JpgImage jpgImage:shrinkJpgImage.getListJpgImage()){

        }
    }
}
