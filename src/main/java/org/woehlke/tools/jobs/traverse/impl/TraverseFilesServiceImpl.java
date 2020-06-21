package org.woehlke.tools.jobs.traverse.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.woehlke.tools.config.properties.ToolsApplicationProperties;
import org.woehlke.tools.jobs.common.impl.AbstractJobServiceImpl;
import org.woehlke.tools.jobs.traverse.filter.FileFilterDirectory;
import org.woehlke.tools.jobs.traverse.TraverseFilesService;
import org.woehlke.tools.model.entities.Job;
import org.woehlke.tools.model.services.JobService;
import org.woehlke.tools.model.services.LogbuchServiceAsync;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayDeque;
import java.util.Deque;

import static org.woehlke.tools.model.config.JobEventSignal.INFO;
import static org.woehlke.tools.model.config.JobEventType.TRAVERSE_FILES;

@Service
public class TraverseFilesServiceImpl extends AbstractJobServiceImpl implements TraverseFilesService {

    private final Deque<File> result = new ArrayDeque<>();
    private final FileFilterDirectory fileFilterDirectory;

    @Autowired
    public TraverseFilesServiceImpl(
        LogbuchServiceAsync logbuchServiceAsync,
        JobService jobService,
        ToolsApplicationProperties properties
    ) {
        super(
            logbuchServiceAsync,
            jobService,
            null,
            null,
            properties);
        this.fileFilterDirectory = new FileFilterDirectory();
    }

    private FileFilter fileFilterFiles;
    private Job job;

    public void add(
        final Job job,
        final FileFilter fileFilterFiles
    ) {
        this.job = job;
        this.fileFilterFiles = fileFilterFiles;
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
                File filesOfDir[] = subdir.listFiles(fileFilterFiles);
                if((filesOfDir != null)&&(filesOfDir.length > 0)){
                    for(File fileOfDir:filesOfDir){
                        result.push(fileOfDir);
                        info( "FILE:  " + fileOfDir.getAbsolutePath(),INFO, TRAVERSE_FILES);
                    }
                }
                File nextsubdirs[] = subdir.listFiles(fileFilterDirectory);
                if((nextsubdirs != null)&&(nextsubdirs.length > 0)) {
                    traverseSubDirs(nextsubdirs);
                }
            }
        }
    }

    public Deque<File> getResult() {
        return result;
    }

}
