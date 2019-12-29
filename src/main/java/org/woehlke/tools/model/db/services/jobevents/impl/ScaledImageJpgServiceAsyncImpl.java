package org.woehlke.tools.model.db.services.jobevents.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.woehlke.tools.model.db.entities.jobevents.ScaledImageJpg;
import org.woehlke.tools.model.db.services.impl.JobEventServiceAsyncImpl;
import org.woehlke.tools.model.db.services.jobevents.ScaledImageJpgService;
import org.woehlke.tools.model.db.services.jobevents.ScaledImageJpgServiceAsync;

@Service
public class ScaledImageJpgServiceAsyncImpl extends JobEventServiceAsyncImpl<ScaledImageJpg> implements ScaledImageJpgServiceAsync {

    @Autowired
    public ScaledImageJpgServiceAsyncImpl(ScaledImageJpgService jobEventService) {
        super(jobEventService);
    }
}
