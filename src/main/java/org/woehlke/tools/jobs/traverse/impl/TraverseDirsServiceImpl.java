package org.woehlke.tools.jobs.traverse.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.woehlke.tools.config.properties.ToolsApplicationProperties;
import org.woehlke.tools.jobs.common.impl.AbstractJobServiceImpl;
import org.woehlke.tools.jobs.images.resize.impl.JobImagesResizeJpgServiceImpl;
import org.woehlke.tools.jobs.traverse.TraverseDirsService;
import org.woehlke.tools.model.entities.Job;
import org.woehlke.tools.model.services.JobService;
import org.woehlke.tools.model.services.LogbuchServiceAsync;

import java.io.File;
import java.io.FileFilter;
import java.util.*;

import static org.woehlke.tools.model.config.JobEventSignal.DONE;
import static org.woehlke.tools.model.config.JobEventSignal.START;
import static org.woehlke.tools.model.config.JobEventType.TRAVERSE_DIRS;

@Service
public class TraverseDirsServiceImpl extends AbstractJobServiceImpl implements TraverseDirsService {

    @Autowired
    public TraverseDirsServiceImpl(
        LogbuchServiceAsync logbuchServiceAsync,
        JobService jobService,
        ToolsApplicationProperties properties
    ) {
       super(
           logbuchServiceAsync,
           jobService,
           null,
           null,
           properties
       );
    }

    private final Deque<File> result = new ArrayDeque<>();
    private FileFilter fileFilterDirectories;

    @Override
    public void add(
        final Job job,
        final FileFilter fileFilterDirectories
    ) {
        this.job = job;
        this.fileFilterDirectories = fileFilterDirectories;
    }

    @Override
    public void run() {
        info(START,TRAVERSE_DIRS);
        File dataRoot = new File(job.getRootDirectory());
        File subdirs[] = {dataRoot};
        traverseSubDirs(subdirs);
        info(DONE,TRAVERSE_DIRS);
    }

    private void traverseSubDirs(File subdirs[]){
        for(File subdir:subdirs) {
            if (subdir.isDirectory()) {
                result.push(subdir);
                info("cd " +subdir.getAbsolutePath(),START,TRAVERSE_DIRS);
                File nextsubdirs[] = subdir.listFiles( this.fileFilterDirectories);
                traverseSubDirs(nextsubdirs);
                info("cd .. (" +subdir.getAbsolutePath()+")", DONE,TRAVERSE_DIRS);
            }
        }
    }

    @Override
    public Deque<File> getResult() {
        return result;
    }

    private Log logger = LogFactory.getLog(JobImagesResizeJpgServiceImpl.class);

}
