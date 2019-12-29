package org.woehlke.tools.model.traverse.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.woehlke.tools.config.application.ToolsApplicationProperties;
import org.woehlke.tools.model.traverse.filter.FileFilterDirectory;
import org.woehlke.tools.model.jobs.common.LogbuchQueueService;
import org.woehlke.tools.model.traverse.TraverseDirs;

import java.io.File;
import java.io.FileFilter;
import java.util.*;

@Component("traverseDirs")
public class TraverseDirsImpl implements TraverseDirs {

    private final FileFilterDirectory filterDirs;
    private final ToolsApplicationProperties properties;

    @Autowired
    public TraverseDirsImpl(final FileFilterDirectory filterDirs, ToolsApplicationProperties properties) {
        this.filterDirs = filterDirs;
        this.properties = properties;
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
