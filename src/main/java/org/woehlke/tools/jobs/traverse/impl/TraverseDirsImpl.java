package org.woehlke.tools.jobs.traverse.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.woehlke.tools.jobs.common.FileFilterDirectory;
import org.woehlke.tools.jobs.common.FileFilterFile;
import org.woehlke.tools.db.LogbuchQueueService;
import org.woehlke.tools.jobs.traverse.TraverseDirs;

import java.io.File;
import java.util.*;

@Component("traverseDirs")
public class TraverseDirsImpl implements TraverseDirs {

    private final LogbuchQueueService log;
    private final FileFilterDirectory filterDirs;
    private final FileFilterFile filterFiles;

    @Autowired
    public TraverseDirsImpl(final LogbuchQueueService log,
                            final FileFilterDirectory filterDirs,
                            final FileFilterFile filterFiles) {
        this.log = log;
        this.filterDirs = filterDirs;
        this.filterFiles = filterFiles;
    }

    private String dataRootDir;
    private boolean dryRun;

    public void add(final String dataRootDir, final boolean dryRun) {
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
                result.push(subdir);
                log.info("cd " +subdir.getAbsolutePath());
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
