package org.woehlke.tools.model.db.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.woehlke.tools.model.db.entities.RenamedOneDirectory;
import org.woehlke.tools.model.db.common.impl.JobEventServiceAsyncImpl;
import org.woehlke.tools.model.db.services.RenamedOneDirectoryService;
import org.woehlke.tools.model.db.services.RenamedOneDirectoryServiceAsync;

@Service
public class RenamedOneDirectoryServiceAsyncImpl extends JobEventServiceAsyncImpl<RenamedOneDirectory> implements RenamedOneDirectoryServiceAsync {

    @Autowired
    public RenamedOneDirectoryServiceAsyncImpl(RenamedOneDirectoryService renamedOneDirectoryService) {
       super(renamedOneDirectoryService);
    }
}
