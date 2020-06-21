package org.woehlke.tools.jobs.rename.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.woehlke.tools.config.properties.ToolsApplicationProperties;
import org.woehlke.tools.jobs.rename.RenameJobGroupServiceService;
import org.woehlke.tools.jobs.rename.RenameDirectoriesJobService;
import org.woehlke.tools.jobs.rename.RenameFilesJobService;
import org.woehlke.tools.jobs.common.impl.AbstractJobGroupServiceImpl;
import org.woehlke.tools.model.config.JobCase;
import org.woehlke.tools.model.entities.Job;
import org.woehlke.tools.model.entities.JobGroup;
import org.woehlke.tools.model.services.JobGroupService;
import org.woehlke.tools.model.services.JobService;

import java.io.File;

@Service
public class RenameJobGroupServiceServiceImpl extends AbstractJobGroupServiceImpl
    implements RenameJobGroupServiceService {

    private final RenameDirectoriesJobService renameDirectoriesJobService;
    private final RenameFilesJobService renameFilesJobService;
    private final ToolsApplicationProperties properties;
    private final JobService jobService;
    private final JobGroupService jobGroupService;

    @Autowired
    public RenameJobGroupServiceServiceImpl(
        RenameDirectoriesJobService renameDirectoriesJobService,
        RenameFilesJobService renameFilesJobService,
        ToolsApplicationProperties properties,
        JobService jobService,
        JobGroupService jobGroupService
    ) {
        this.renameDirectoriesJobService = renameDirectoriesJobService;
        this.renameFilesJobService = renameFilesJobService;
        this.properties = properties;
        this.jobService = jobService;
        this.jobGroupService = jobGroupService;
    }

    @Override
    public void setRootDirectory(File rootDirectory) {
        JobGroup jobGroup = new JobGroup(
            rootDirectory,
            properties.getDryRun(),
            properties.getDbActive()
        );
        Job jobRenameDirectoriesObject = Job.create(
            JobCase.JOB_RENAME_DIRECTORIES,
            rootDirectory,
            properties.getDryRun(),
            properties.getDbActive()
        );
        jobRenameDirectoriesObject = jobService.add(jobRenameDirectoriesObject);
        Job jobRenameFilesObject = Job.create(
            JobCase.JOB_RENAME_FILES,
            rootDirectory,
            properties.getDryRun(),
            properties.getDbActive()
        );
        jobRenameFilesObject = jobService.add(jobRenameFilesObject);
        this.renameDirectoriesJobService.setRootDirectory(jobRenameDirectoriesObject);
        this.renameFilesJobService.setRootDirectory(jobRenameFilesObject);
        jobGroup.getJobSet().add(jobRenameFilesObject);
        jobGroup.getJobSet().add(jobRenameFilesObject);
        this.jobGroupService.add(jobGroup);
    }

    @Override
    public String getJobName() {
        return JobCase.JOB_RENAME_DIRECTORIES.getHumanReadable()
                + " and "
                + JobCase.JOB_RENAME_FILES.getHumanReadable();
    }

    @Override
    public void run() {
        this.renameDirectoriesJobService.run();
        this.renameFilesJobService.run();
    }
}
