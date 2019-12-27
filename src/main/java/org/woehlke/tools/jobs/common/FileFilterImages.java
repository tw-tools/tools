package org.woehlke.tools.jobs.common;

import java.io.File;
import java.io.FileFilter;

public class FileFilterImages implements FileFilter {

    @Override
    public boolean accept(File pathname) {
        boolean result = pathname.isFile()
            && ( !pathname.isDirectory() )
            && (
                (      pathname.getAbsolutePath().endsWith(".jpg")
                    || pathname.getAbsolutePath().endsWith(".jpeg")
                    || pathname.getAbsolutePath().endsWith(".JPG")
                    || pathname.getAbsolutePath().endsWith(".JPEG")
                )
            );
        return result ;
    }
}
