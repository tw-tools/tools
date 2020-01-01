package org.woehlke.tools.model.db.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.woehlke.tools.model.db.entities.ScaledImageJpg;
import org.woehlke.tools.model.db.common.impl.JobEventServiceAsyncImpl;
import org.woehlke.tools.model.db.services.ScaledImageJpgService;
import org.woehlke.tools.model.db.services.ScaledImageJpgServiceAsync;

@Service
public class ScaledImageJpgServiceAsyncImpl extends JobEventServiceAsyncImpl<ScaledImageJpg> implements ScaledImageJpgServiceAsync {

    @Autowired
    public ScaledImageJpgServiceAsyncImpl(ScaledImageJpgService jobEventService) {
        super(jobEventService);
    }
}
