package org.woehlke.tools.filesystem;

import org.woehlke.tools.filesystem.impl.FileFilterDirectory;

import java.io.File;
import java.util.*;

public class TraverseDirs implements Runnable {

    private final String dataRootDir;
    private final Deque<File> dirs = new ArrayDeque<File>();

    public TraverseDirs(String dataRootDir) {
        this.dataRootDir = dataRootDir;
    }

    @Override
    public void run() {
        File dataRoot = new File(dataRootDir);
        File subdirs[] = {dataRoot};
        traverseSubDirs(subdirs);
    }

    private void traverseSubDirs(File subdirs[]){
        FileFilterDirectory filter = new FileFilterDirectory();
        for(File subdir:subdirs) {
            if (subdir.isDirectory()) {
                dirs.push(subdir);
                System.out.println("cd " +subdir.getAbsolutePath());
                File nextsubdirs[] = subdir.listFiles(filter);
                traverseSubDirs(nextsubdirs);
            }
        }
        System.out.println("cd ..");
    }

    public String getDataRootDir() {
        return dataRootDir;
    }

    public Deque<File> getDirs() {
        return dirs;
    }
}
