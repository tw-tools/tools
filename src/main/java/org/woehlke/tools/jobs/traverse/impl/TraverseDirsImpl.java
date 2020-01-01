package org.woehlke.tools.jobs.traverse.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.woehlke.tools.jobs.images.resize.impl.JobImagesResizeJpgImpl;
import org.woehlke.tools.jobs.traverse.TraverseDirs;
import org.woehlke.tools.model.entities.Job;

import java.io.File;
import java.io.FileFilter;
import java.util.*;

@Component
public class TraverseDirsImpl implements TraverseDirs {

    public TraverseDirsImpl() {}

    private final Deque<File> result = new ArrayDeque<File>();
    private FileFilter fileFilter;
    private Job job;

    @Override
    public void add(final Job job,
                    final FileFilter fileFilter
    ) {
        this.job = job;
        this.fileFilter = fileFilter;
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
                result.push(subdir);
                logger.info("cd " +subdir.getAbsolutePath());
                File nextsubdirs[] = subdir.listFiles(this.fileFilter);
                traverseSubDirs(nextsubdirs);
                logger.info("cd ..");
            }
        }
    }

    @Override
    public Deque<File> getResult() {
        return result;
    }

    private Log logger = LogFactory.getLog(JobImagesResizeJpgImpl.class);

}
