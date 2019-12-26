package org.woehlke.tools.jobs.traverse.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.woehlke.tools.jobs.common.FileFilterDirectory;
import org.woehlke.tools.jobs.common.FileFilterFile;
import org.woehlke.tools.db.LogbuchQueueService;
import org.woehlke.tools.jobs.traverse.TraverseFiles;

import java.io.File;
import java.util.ArrayDeque;
import java.util.Deque;

@Component("traverseFiles")
public class TraverseFilesImpl implements TraverseFiles {

    private final LogbuchQueueService log;
    private final FileFilterDirectory filterDirs;
    private final FileFilterFile filterFiles;

    @Autowired
    public TraverseFilesImpl(final LogbuchQueueService log,
                             final FileFilterDirectory filterDirs,
                             final FileFilterFile filterFiles) {
        this.log=log;
        this.filterDirs=filterDirs;
        this.filterFiles=filterFiles;
    }

    private String dataRootDir;
    private boolean dryRun;

    public void add(final String dataRootDir,final boolean dryRun) {
        this.dataRootDir = dataRootDir;
        this.dryRun = dryRun;
    }

    @Override
    public void run() {
        File dataRoot = new File(dataRootDir);
        File subdirs[] = {dataRoot};
        traverseSubDirs(subdirs);
    }

    private final Deque<File> result = new ArrayDeque<File>();

    private void traverseSubDirs(File subdirs[]){
        for(File subdir:subdirs) {
            if (subdir.isDirectory()) {
                log.info("cd " +subdir.getAbsolutePath());
                File filesOfDir[] = subdir.listFiles(filterFiles);
                for(File fileOfDir:filesOfDir){
                    result.push(fileOfDir);
                    log.info("File: " +fileOfDir.getAbsolutePath());
                }
                File nextsubdirs[] = subdir.listFiles(filterDirs);
                traverseSubDirs(nextsubdirs);
                log.info("cd ..");
            }
        }
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
