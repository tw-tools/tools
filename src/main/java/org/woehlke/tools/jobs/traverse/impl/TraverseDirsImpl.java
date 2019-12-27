package org.woehlke.tools.jobs.traverse.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.woehlke.tools.jobs.common.FileFilterDirectory;
import org.woehlke.tools.jobs.common.LogbuchQueueService;
import org.woehlke.tools.jobs.traverse.TraverseDirs;

import java.io.File;
import java.io.FileFilter;
import java.util.*;

@Component("traverseDirs")
public class TraverseDirsImpl implements TraverseDirs {

    private final FileFilterDirectory filterDirs;

    @Autowired
    public TraverseDirsImpl(final FileFilterDirectory filterDirs) {
        this.filterDirs = filterDirs;
    }

    private String dataRootDir;
    private LogbuchQueueService log;

    public void add(final String dataRootDir,
                    final LogbuchQueueService log,
                    final FileFilter filterFiles) {
        this.dataRootDir = dataRootDir;
        this.log = log;
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
                log.info("DIR:  " +subdir.getAbsolutePath());
                File nextsubdirs[] = subdir.listFiles(filterDirs);
                traverseSubDirs(nextsubdirs);
                //log.info("cd ..");
            }
        }
    }

    public Deque<File> getResult() {
        return result;
    }

}
