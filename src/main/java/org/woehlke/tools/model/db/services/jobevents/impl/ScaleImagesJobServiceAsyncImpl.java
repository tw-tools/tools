package org.woehlke.tools.model.db.services.jobevents.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.woehlke.tools.model.db.entities.jobevents.ScaleImagesJob;
import org.woehlke.tools.model.db.services.impl.JobEventServiceAsyncImpl;
import org.woehlke.tools.model.db.services.jobevents.ScaleImagesJobService;
import org.woehlke.tools.model.db.services.jobevents.ScaleImagesJobServiceAsync;

@Service
public class ScaleImagesJobServiceAsyncImpl extends JobEventServiceAsyncImpl<ScaleImagesJob> implements ScaleImagesJobServiceAsync {

    @Autowired
    public ScaleImagesJobServiceAsyncImpl(ScaleImagesJobService jobEventService) {
        super(jobEventService);
    }
}
