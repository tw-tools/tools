package org.woehlke.tools.jobs.images.info.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.woehlke.tools.config.properties.ApplicationProperties;
import org.woehlke.tools.jobs.common.impl.AbstractJobServiceImpl;
import org.woehlke.tools.jobs.images.info.ImagesInfoPngJobService;
import org.woehlke.tools.jobs.traverse.TraverseDirsService;
import org.woehlke.tools.jobs.traverse.TraverseFilesService;
import org.woehlke.tools.model.config.JobCase;
import org.woehlke.tools.model.entities.Job;
import org.woehlke.tools.model.services.JobService;
import org.woehlke.tools.model.services.LogbuchServiceAsync;

import static org.woehlke.tools.model.config.JobEventSignal.DONE;
import static org.woehlke.tools.model.config.JobEventSignal.START;
import static org.woehlke.tools.model.config.JobEventType.*;

@Service
public class ImagesInfoPngJobServiceImpl extends AbstractJobServiceImpl implements ImagesInfoPngJobService {

    @Autowired
    public ImagesInfoPngJobServiceImpl(
        LogbuchServiceAsync logbuchServiceAsync,
        TraverseDirsService traverseDirsService,
        TraverseFilesService traverseFilesService,
        JobService jobService,
        ApplicationProperties properties
    ) {
        super(
            logbuchServiceAsync,
            jobService,
            traverseDirsService,
            traverseFilesService,
            properties
        );
    }

    @Override
    public void setRootDirectory(Job job) {
        this.job=job;
    }

    @Override
    public String getJobName() {
        return JobCase.JOB_IMAGES_INFO_PNG.getHumanReadable();
    }

    @Override
    public void run() {
        signalJobStartToDb(COLLECT_INFO_PNG_IMAGES);
        line();
        this.traverseDirsService.run();
        line();
        this.traverseFilesService.run();
        line();
        collectImagesInfoPng();
        line();
        signalJobDoneToDb(COLLECT_INFO_PNG_IMAGES);
    }

    private void collectImagesInfoPng(){
        info(START,COLLECT_INFO_ONE_PNG_IMAGE);
        line();
        info(DONE,COLLECT_INFO_ONE_PNG_IMAGE);
    }
}
