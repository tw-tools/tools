package org.woehlke.tools.filesystem.impl;

import java.io.File;
import java.io.FileFilter;

public class FileFilterFile implements FileFilter {

    @Override
    public boolean accept(File pathname) {
        return ( !pathname.isDirectory() ) && pathname.isFile() ;
    }
}
