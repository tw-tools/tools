package org.woehlke.tools.jobs.images.info.impl;

import org.springframework.stereotype.Service;
import org.woehlke.tools.jobs.images.info.JobImagesInfoJpgService;
import org.woehlke.tools.model.config.JobCase;
import org.woehlke.tools.model.entities.Job;

@Service
public class JobImagesInfoJpgServiceImpl extends Thread implements JobImagesInfoJpgService {

    private Job job;

    @Override
    public void setRootDirectory(Job job) {
        this.job=job;
    }

    @Override
    public String getJobName() {
        return JobCase.JOB_IMAGES_INFO_JPG.getHumanReadable();
    }

    @Override
    public void run() {

    }
}
