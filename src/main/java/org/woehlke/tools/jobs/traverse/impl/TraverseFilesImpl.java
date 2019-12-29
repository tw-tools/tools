package org.woehlke.tools.jobs.traverse.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.woehlke.tools.config.ToolsApplicationProperties;
import org.woehlke.tools.jobs.common.FileFilterDirectory;
import org.woehlke.tools.jobs.common.LogbuchQueueService;
import org.woehlke.tools.jobs.traverse.TraverseFiles;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayDeque;
import java.util.Deque;

@Component("traverseFiles")
public class TraverseFilesImpl implements TraverseFiles {

    private final Deque<File> result = new ArrayDeque<File>();
    private final FileFilterDirectory filterDirs;
    private final ToolsApplicationProperties properties;

    @Autowired
    public TraverseFilesImpl(final FileFilterDirectory filterDirs, ToolsApplicationProperties properties) {
        this.filterDirs=filterDirs;
        this.properties = properties;
    }

    private FileFilter filterFiles;
    private String dataRootDir;
    private LogbuchQueueService log;

    public void add(
        final String dataRootDir,
        final LogbuchQueueService log,
        final FileFilter filterFiles
    ) {
        this.dataRootDir = dataRootDir;
        this.log = log;
        this.filterFiles = filterFiles;
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
                File filesOfDir[] = subdir.listFiles(filterFiles);
                for(File fileOfDir:filesOfDir){
                    result.push(fileOfDir);
                    log.info("FILE: " + fileOfDir.getAbsolutePath());
                }
                File nextsubdirs[] = subdir.listFiles(filterDirs);
                traverseSubDirs(nextsubdirs);
            }
        }
    }

    public Deque<File> getResult() {
        return result;
    }

}
