package org.woehlke.tools.model.jobgroups.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.woehlke.tools.model.jobgroups.JobGroupRename;
import org.woehlke.tools.model.jobs.JobRenameDirectories;
import org.woehlke.tools.model.jobs.JobRenameFiles;

import java.io.File;

@Component
public class JobGroupRenameImpl extends AbstractJobGroupImpl implements JobGroupRename {

    private final JobRenameDirectories jobRenameDirectories;
    private final JobRenameFiles jobRenameFiles;

    @Autowired
    public JobGroupRenameImpl(JobRenameDirectories jobRenameDirectories, JobRenameFiles jobRenameFiles) {
        this.jobRenameDirectories = jobRenameDirectories;
        this.jobRenameFiles = jobRenameFiles;
    }

    @Override
    public void setRootDirectory(File rootDirectory) {
        this.jobRenameDirectories.setRootDirectory(rootDirectory);
        this.jobRenameFiles.setRootDirectory(rootDirectory);
    }

    @Override
    public String getJobName() {
        return jobRenameDirectories.getJobName() + " and "+ jobRenameFiles.getJobName();
    }

    @Override
    public void run() {
        this.jobRenameDirectories.run();
        this.jobRenameFiles.run();
    }
}
