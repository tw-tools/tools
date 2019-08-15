package org.woehlke.tools.filesystem;

import org.woehlke.tools.filesystem.impl.FileFilterDirectory;
import org.woehlke.tools.filesystem.impl.FileFilterFile;

import java.io.File;
import java.util.ArrayDeque;
import java.util.Deque;

public class TraverseFiles implements Traverse {

    private final String dataRootDir;
    private final boolean dryRun;

    private final Deque<File> result = new ArrayDeque<File>();


    private final FileFilterDirectory filterDirs = new FileFilterDirectory();
    private final FileFilterFile filterFiles = new FileFilterFile();

    public TraverseFiles(String dataRootDir,final boolean dryRun) {
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
                System.out.println("cd " +subdir.getAbsolutePath());
                File filesOfDir[] = subdir.listFiles(filterFiles);
                for(File fileOfDir:filesOfDir){
                    result.push(fileOfDir);
                    System.out.println("File: " +fileOfDir.getAbsolutePath());
                }
                File nextsubdirs[] = subdir.listFiles(filterDirs);
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
