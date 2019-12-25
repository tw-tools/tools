package org.woehlke.tools.beans;

import javax.swing.filechooser.FileFilter;
import java.io.File;

public class DirectoryFileFilter extends FileFilter {

    @Override
    public boolean accept(File f) {
        return f.isDirectory() && f.canRead() && f.canWrite();
    }

    @Override
    public String getDescription() {
        return "insures, that the file is a directory";
    }
}
