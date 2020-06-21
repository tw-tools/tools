package org.woehlke.tools.jobs.traverse.filter;

import org.woehlke.tools.jobs.rename.impl.ToolsFilenameTransform;

import java.io.File;
import java.io.FileFilter;

public class FileFilterToBeRenamedFile implements FileFilter {

    @Override
    public boolean accept(File pathname) {
        if((!pathname.isDirectory()) && pathname.isFile() && pathname.canRead() && pathname.canWrite()){
            String name = pathname.getName();
            if(ToolsFilenameTransform.toBeRenamed(name)){
                return true;
            }
        }
        return false;
    }
}
