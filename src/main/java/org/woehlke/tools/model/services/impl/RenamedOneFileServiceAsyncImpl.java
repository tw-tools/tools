package org.woehlke.tools.model.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.woehlke.tools.model.entities.RenamedOneFile;
import org.woehlke.tools.model.common.impl.JobEventServiceAsyncImpl;
import org.woehlke.tools.model.services.RenamedOneFileService;
import org.woehlke.tools.model.services.RenamedOneFileServiceAsync;

@Service
public class RenamedOneFileServiceAsyncImpl extends JobEventServiceAsyncImpl<RenamedOneFile> implements RenamedOneFileServiceAsync {

    @Autowired
    public RenamedOneFileServiceAsyncImpl(RenamedOneFileService jobEventService) {
        super(jobEventService);
    }
}
