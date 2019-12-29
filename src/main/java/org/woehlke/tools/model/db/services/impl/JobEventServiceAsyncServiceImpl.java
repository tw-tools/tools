package org.woehlke.tools.model.db.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.woehlke.tools.model.db.entities.JobEventRenameFilesJob;
import org.woehlke.tools.model.db.entities.JobEventRenamedOneDirectory;
import org.woehlke.tools.model.db.entities.JobEventRenamedOneFile;
import org.woehlke.tools.model.db.entities.JobEventScaleImagesJob;
import org.woehlke.tools.model.db.services.JobEventService;
import org.woehlke.tools.model.db.services.JobEventServiceAsyncService;

@Service("jobEventServiceAsyncService")
public class JobEventServiceAsyncServiceImpl implements JobEventServiceAsyncService {

    private final JobEventService jobEventService;

    @Autowired
    public JobEventServiceAsyncServiceImpl(JobEventService jobEventService) {
        this.jobEventService = jobEventService;
    }

    @Async
    @Override
    public JobEventScaleImagesJob add(JobEventScaleImagesJob p) {
        return this.jobEventService.add(p);
    }

    @Async
    @Override
    public JobEventRenameFilesJob add(JobEventRenameFilesJob p) {
        return  this.jobEventService.add(p);
    }

    @Async
    @Override
    public JobEventRenamedOneFile add(JobEventRenamedOneFile p) {
        return  this.jobEventService.add(p);
    }

    @Async
    @Override
    public JobEventRenamedOneDirectory add(JobEventRenamedOneDirectory p) {
        return  this.jobEventService.add(p);
    }
}
