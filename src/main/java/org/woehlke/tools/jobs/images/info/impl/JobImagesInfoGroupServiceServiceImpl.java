package org.woehlke.tools.jobs.images.info.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.woehlke.tools.config.properties.ToolsApplicationProperties;
import org.woehlke.tools.jobs.images.info.JobImagesInfoGroupServiceService;
import org.woehlke.tools.jobs.images.info.JobImagesInfoJpgService;
import org.woehlke.tools.jobs.images.info.ImagesInfoPngJobService;
import org.woehlke.tools.jobs.common.impl.AbstractJobGroupServiceImpl;
import org.woehlke.tools.model.config.JobCase;
import org.woehlke.tools.model.entities.Job;
import org.woehlke.tools.model.entities.JobGroup;
import org.woehlke.tools.model.services.JobGroupService;
import org.woehlke.tools.model.services.JobService;

import java.io.File;

@Service
public class JobImagesInfoGroupServiceServiceImpl extends AbstractJobGroupServiceImpl implements JobImagesInfoGroupServiceService {

    private final JobImagesInfoJpgService jobImagesInfoJpgService;
    private final ImagesInfoPngJobService imagesInfoPngJobService;
    private final ToolsApplicationProperties properties;
    private final JobService jobService;
    private final JobGroupService jobGroupService;

    @Autowired
    public JobImagesInfoGroupServiceServiceImpl(
        JobImagesInfoJpgService jobImagesInfoJpgService,
        ImagesInfoPngJobService imagesInfoPngJobService,
        ToolsApplicationProperties properties,
        JobService jobService,
        JobGroupService jobGroupService
    ) {
        this.jobImagesInfoJpgService = jobImagesInfoJpgService;
        this.imagesInfoPngJobService = imagesInfoPngJobService;
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
        this.jobImagesInfoJpgService.setRootDirectory(jobImagesInfoJpgObject);
        this.imagesInfoPngJobService.setRootDirectory(jobImagesInfoPngObject);
    }

    @Override
    public String getJobName() {
        return JobCase.JOB_IMAGES_INFO_JPG.getHumanReadable()
            + " and " +
            JobCase.JOB_IMAGES_INFO_PNG.getHumanReadable();
    }

    @Override
    public void run() {
        this.jobImagesInfoJpgService.run();
        this.imagesInfoPngJobService.run();
    }
}
