package org.woehlke.tools.filesystem.impl;

import java.io.File;
import java.io.FileFilter;

public class FileFilterDirectory implements FileFilter {

    @Override
    public boolean accept(File pathname) {
        return pathname.isDirectory();
    }
}
