package org.woehlke.tools.jobs.traverse.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.woehlke.tools.jobs.common.FileFilterDirectory;
import org.woehlke.tools.jobs.mq.LogbuchQueueService;
import org.woehlke.tools.jobs.traverse.TraverseDirs;

import java.io.File;
import java.util.*;

@Component("traverseDirs")
public class TraverseDirsImpl implements TraverseDirs {

    private final LogbuchQueueService log;
    private final FileFilterDirectory filterDirs;

    @Autowired
    public TraverseDirsImpl(final LogbuchQueueService log,
                            final FileFilterDirectory filterDirs) {
        this.log = log;
        this.filterDirs = filterDirs;
    }

    private String dataRootDir;


    public void add(final String dataRootDir) {
        this.dataRootDir = dataRootDir;

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


    public Deque<File> getResult() {
        return result;
    }


}
