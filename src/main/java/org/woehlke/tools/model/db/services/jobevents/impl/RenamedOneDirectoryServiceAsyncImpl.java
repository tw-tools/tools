package org.woehlke.tools.model.db.services.jobevents.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.woehlke.tools.model.db.entities.jobevents.RenamedOneDirectory;
import org.woehlke.tools.model.db.services.impl.JobEventServiceAsyncImpl;
import org.woehlke.tools.model.db.services.jobevents.RenamedOneDirectoryService;
import org.woehlke.tools.model.db.services.jobevents.RenamedOneDirectoryServiceAsync;

@Service
public class RenamedOneDirectoryServiceAsyncImpl extends JobEventServiceAsyncImpl<RenamedOneDirectory> implements RenamedOneDirectoryServiceAsync {

    @Autowired
    public RenamedOneDirectoryServiceAsyncImpl(RenamedOneDirectoryService renamedOneDirectoryService) {
       super(renamedOneDirectoryService);
    }
}
