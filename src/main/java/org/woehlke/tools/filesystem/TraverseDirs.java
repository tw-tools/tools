package org.woehlke.tools.filesystem;

import org.woehlke.tools.filesystem.impl.FileFilterDirectory;

import java.io.File;
import java.util.*;

public class TraverseDirs implements Traverse {

    private final String dataRootDir;
    private final boolean dryRun;
    private final Deque<File> result = new ArrayDeque<File>();

    private final FileFilterDirectory filter;

    public TraverseDirs(String dataRootDir,final boolean dryRun) {
        this.filter = new FileFilterDirectory();
        this.dataRootDir = dataRootDir;
        this.dryRun = dryRun;
    }

    @Override
    public void run() {
        File dataRoot = new File(dataRootDir);
        File subdirs[] = {dataRoot};
        traverseSubDirs(subdirs);
    }

    private void traverseSubDirs(File subdirs[]){
        for(File subdir:subdirs) {
            if (subdir.isDirectory()) {
                result.push(subdir);
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

    public Deque<File> getResult() {
        return result;
    }

    public boolean isDryRun() {
        return dryRun;
    }
}
