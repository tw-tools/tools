package org.woehlke.tools.jobs.common;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileFilter;

@Component
public class FileFilterFile implements FileFilter {

    @Override
    public boolean accept(File pathname) {
        return ( !pathname.isDirectory() ) && pathname.isFile() ;
    }
}
