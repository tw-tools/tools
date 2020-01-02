package org.woehlke.tools.jobs.traverse.filter;


import java.io.File;
import java.io.FileFilter;

public class FileFilterFile implements FileFilter {

    @Override
    public boolean accept(File pathname) {
        return ( !pathname.isDirectory() ) && pathname.isFile()  && pathname.canRead() && pathname.canWrite();
    }
}
