package org.woehlke.tools.jobs.images.info.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.woehlke.tools.config.properties.ApplicationProperties;
import org.woehlke.tools.jobs.images.info.JobImagesInfoGroup;
import org.woehlke.tools.jobs.images.info.JobImagesInfoJpg;
import org.woehlke.tools.jobs.images.info.JobImagesInfoPng;
import org.woehlke.tools.jobs.common.impl.AbstractJobGroupImpl;
import org.woehlke.tools.model.db.config.JobCase;
import org.woehlke.tools.model.db.entities.Job;
import org.woehlke.tools.model.db.entities.JobGroup;
import org.woehlke.tools.model.db.services.JobGroupService;
import org.woehlke.tools.model.db.services.JobService;

import java.io.File;

@Component
public class JobImagesInfoGroupImpl extends AbstractJobGroupImpl implements JobImagesInfoGroup {

    private final JobImagesInfoJpg jobImagesInfoJpg;
    private final JobImagesInfoPng jobImagesInfoPng;
    private final ApplicationProperties properties;
    private final JobService jobService;
    private final JobGroupService jobGroupService;

    @Autowired
    public JobImagesInfoGroupImpl(JobImagesInfoJpg jobImagesInfoJpg, JobImagesInfoPng jobImagesInfoPng, ApplicationProperties properties, JobService jobService, JobGroupService jobGroupService) {
        this.jobImagesInfoJpg = jobImagesInfoJpg;
        this.jobImagesInfoPng = jobImagesInfoPng;
        this.properties = properties;
        this.jobService = jobService;
        this.jobGroupService = jobGroupService;
    }

    @Override
    public void setRootDirectory(File rootDirectory) {
        JobGroup jobGroup = new JobGroup(properties.getDryRun(),properties.getDbActive());
        Job jobImagesInfoJpgObject = Job.create(
            JobCase.JOB_RENAME_DIRECTORIES,rootDirectory,properties.getDryRun(),properties.getDbActive()
        );
        jobImagesInfoJpgObject = jobService.add(jobImagesInfoJpgObject);
        Job jobImagesInfoPngObject = Job.create(
            JobCase.JOB_RENAME_FILES,rootDirectory,properties.getDryRun(),properties.getDbActive()
        );
        jobImagesInfoPngObject = jobService.add(jobImagesInfoPngObject);
        this.jobImagesInfoJpg.setRootDirectory(jobImagesInfoJpgObject);
        this.jobImagesInfoPng.setRootDirectory(jobImagesInfoPngObject);
        jobGroup.getJobSet().add(jobImagesInfoJpgObject);
        jobGroup.getJobSet().add(jobImagesInfoPngObject);
        this.jobGroupService.add(jobGroup);
    }

    @Override
    public String getJobName() {
        return this.jobImagesInfoJpg.getJobName() + " and " + this.jobImagesInfoPng.getJobName();
    }
}
