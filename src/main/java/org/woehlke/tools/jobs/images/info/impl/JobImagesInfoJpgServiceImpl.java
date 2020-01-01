package org.woehlke.tools.jobs.images.info.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.woehlke.tools.config.properties.ApplicationProperties;
import org.woehlke.tools.jobs.common.impl.AbstractJobServiceImpl;
import org.woehlke.tools.jobs.images.info.JobImagesInfoJpgService;
import org.woehlke.tools.model.config.JobCase;
import org.woehlke.tools.model.entities.Job;
import org.woehlke.tools.model.services.LogbuchServiceAsync;

@Service
public class JobImagesInfoJpgServiceImpl extends AbstractJobServiceImpl implements JobImagesInfoJpgService {

    @Autowired
    public JobImagesInfoJpgServiceImpl(LogbuchServiceAsync logbuchServiceAsync, ApplicationProperties properties) {
        super(logbuchServiceAsync, properties);
    }

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
