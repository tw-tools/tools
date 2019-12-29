package org.woehlke.tools.model.db.services.jobevents.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.woehlke.tools.model.db.entities.jobevents.RenamedOneFile;
import org.woehlke.tools.model.db.services.impl.JobEventServiceAsyncImpl;
import org.woehlke.tools.model.db.services.jobevents.RenamedOneFileService;
import org.woehlke.tools.model.db.services.jobevents.RenamedOneFileServiceAsync;

@Service
public class RenamedOneFileServiceAsyncImpl extends JobEventServiceAsyncImpl<RenamedOneFile> implements RenamedOneFileServiceAsync {

    @Autowired
    public RenamedOneFileServiceAsyncImpl(RenamedOneFileService jobEventService) {
        super(jobEventService);
    }
}
