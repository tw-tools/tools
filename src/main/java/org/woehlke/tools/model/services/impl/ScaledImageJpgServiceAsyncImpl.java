package org.woehlke.tools.model.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.woehlke.tools.model.entities.ScaledImageJpg;
import org.woehlke.tools.model.common.impl.JobEventServiceAsyncImpl;
import org.woehlke.tools.model.services.ScaledImageJpgService;
import org.woehlke.tools.model.services.ScaledImageJpgServiceAsync;

@Service
public class ScaledImageJpgServiceAsyncImpl extends JobEventServiceAsyncImpl<ScaledImageJpg> implements ScaledImageJpgServiceAsync {

    @Autowired
    public ScaledImageJpgServiceAsyncImpl(ScaledImageJpgService jobEventService) {
        super(jobEventService);
    }
}
