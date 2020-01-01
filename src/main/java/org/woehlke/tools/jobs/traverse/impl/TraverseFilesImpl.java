package org.woehlke.tools.jobs.traverse.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.woehlke.tools.config.properties.ApplicationProperties;
import org.woehlke.tools.jobs.traverse.filter.FileFilterDirectory;
import org.woehlke.tools.jobs.traverse.TraverseFiles;
import org.woehlke.tools.model.db.entities.Job;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayDeque;
import java.util.Deque;

@Component("traverseFiles")
public class TraverseFilesImpl implements TraverseFiles {

    private final Deque<File> result = new ArrayDeque<File>();
    private final FileFilterDirectory filterDirs;
    private final ApplicationProperties properties;

    @Autowired
    public TraverseFilesImpl(final FileFilterDirectory filterDirs, ApplicationProperties properties) {
        this.filterDirs=filterDirs;
        this.properties = properties;
    }

    private FileFilter filterFiles;
    private Job job;

    public void add(
        final Job job,
        final FileFilter filterFiles
    ) {
        this.job = job;
        this.filterFiles = filterFiles;
    }

    @Override
    public void run() {
        File dataRoot = new File(job.getRootDirectory());
        File subdirs[] = {dataRoot};
        traverseSubDirs(subdirs);
    }

    private void traverseSubDirs(File subdirs[]){
        for(File subdir:subdirs) {
            if (subdir.isDirectory()) {
                File filesOfDir[] = subdir.listFiles(filterFiles);
                for(File fileOfDir:filesOfDir){
                    result.push(fileOfDir);
                    //log.info("FILE: " + fileOfDir.getAbsolutePath());
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
