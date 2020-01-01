package org.woehlke.tools.jobs.images.info.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.woehlke.tools.config.properties.ApplicationProperties;
import org.woehlke.tools.jobs.images.info.JobImagesInfoGroup;
import org.woehlke.tools.jobs.images.info.JobImagesInfoJpg;
import org.woehlke.tools.jobs.images.info.JobImagesInfoPng;
import org.woehlke.tools.jobs.common.impl.AbstractJobGroupImpl;
import org.woehlke.tools.model.config.JobCase;
import org.woehlke.tools.model.entities.Job;
import org.woehlke.tools.model.entities.JobGroup;
import org.woehlke.tools.model.services.JobGroupService;
import org.woehlke.tools.model.services.JobService;

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

    private JobGroup jobGroup;
    private Job jobImagesInfoJpgObject;
    private Job jobImagesInfoPngObject;

    private void setJobObjects(File rootDirectory){
        jobGroup = new JobGroup(
            rootDirectory,
            properties.getDryRun(),
            properties.getDbActive()
        );
        jobImagesInfoJpgObject = Job.create(
            JobCase.JOB_RENAME_DIRECTORIES,
            rootDirectory,
            properties.getDryRun(),
            properties.getDbActive()
        );
        jobImagesInfoJpgObject = jobService.add(jobImagesInfoJpgObject);
        jobImagesInfoPngObject = Job.create(
            JobCase.JOB_RENAME_FILES,
            rootDirectory,
            properties.getDryRun(),
            properties.getDbActive()
        );
        jobImagesInfoPngObject = jobService.add(jobImagesInfoPngObject);
        jobGroup.getJobSet().add(jobImagesInfoJpgObject);
        jobGroup.getJobSet().add(jobImagesInfoPngObject);
        jobGroup = this.jobGroupService.add(jobGroup);
    }

    @Override
    public void setRootDirectory(File rootDirectory) {
        setJobObjects(rootDirectory);
        this.jobImagesInfoJpg.setRootDirectory(jobImagesInfoJpgObject);
        this.jobImagesInfoPng.setRootDirectory(jobImagesInfoPngObject);
    }

    @Override
    public String getJobName() {
        return JobCase.JOB_IMAGES_INFO_JPG.getHumanReadable()
            + " and " +
            JobCase.JOB_IMAGES_INFO_PNG.getHumanReadable();
    }

    @Override
    public void run() {
        this.jobImagesInfoJpg.run();
        this.jobImagesInfoPng.run();
    }
}
