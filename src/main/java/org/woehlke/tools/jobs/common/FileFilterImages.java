package org.woehlke.tools.jobs.common;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tika.Tika;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

public class FileFilterImages implements FileFilter {

    private final Tika defaultTika = new Tika();

    @Override
    public boolean accept(File pathname) {
        String fileType = "unknown";
        try {
            fileType = defaultTika.detect(pathname);
        } catch (IOException e) {
            String prefix = "IOException: ";
            logger.info(prefix+e.getMessage());
            logger.info(prefix+e.getCause().getMessage());
            for(StackTraceElement el:e.getStackTrace()){
                logger.info(prefix+el.getClassName()+" "+el.getMethodName()+" "+el.getFileName()+" "+el.getLineNumber());
            }
        }
        return (fileType.compareTo("image/jpeg")==0);
    }

    private Log logger = LogFactory.getLog(FileFilterImages.class);
}
