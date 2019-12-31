package org.woehlke.tools.model.db.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.woehlke.tools.model.db.entities.RenamedOneFile;
import org.woehlke.tools.model.db.common.JobEventServiceAsyncImpl;
import org.woehlke.tools.model.db.services.RenamedOneFileService;
import org.woehlke.tools.model.db.services.RenamedOneFileServiceAsync;

@Service
public class RenamedOneFileServiceAsyncImpl extends JobEventServiceAsyncImpl<RenamedOneFile> implements RenamedOneFileServiceAsync {

    @Autowired
    public RenamedOneFileServiceAsyncImpl(RenamedOneFileService jobEventService) {
        super(jobEventService);
    }
}
