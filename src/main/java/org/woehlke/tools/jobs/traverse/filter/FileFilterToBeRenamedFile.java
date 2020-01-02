package org.woehlke.tools.jobs.traverse.filter;

import org.woehlke.tools.jobs.rename.impl.FilenameTransform;

import java.io.File;
import java.io.FileFilter;

public class FileFilterToBeRenamedFile implements FileFilter {

    @Override
    public boolean accept(File pathname) {
        if((!pathname.isDirectory()) && pathname.isFile() && pathname.canRead() && pathname.canWrite()){
            String name = pathname.getName();
            if(FilenameTransform.toBeRenamed(name)){
                return true;
            }
        }
        return false;
    }
}
