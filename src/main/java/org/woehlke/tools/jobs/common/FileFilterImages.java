package org.woehlke.tools.jobs.common;

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
            e.printStackTrace();
        }
        return (fileType.compareTo("image/jpeg")==0);
    }
}
