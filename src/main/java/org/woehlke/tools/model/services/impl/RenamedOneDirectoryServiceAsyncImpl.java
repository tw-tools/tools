package org.woehlke.tools.model.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.woehlke.tools.model.entities.RenamedOneDirectory;
import org.woehlke.tools.model.common.impl.JobEventServiceAsyncImpl;
import org.woehlke.tools.model.services.RenamedOneDirectoryService;
import org.woehlke.tools.model.services.RenamedOneDirectoryServiceAsync;

@Service
public class RenamedOneDirectoryServiceAsyncImpl extends JobEventServiceAsyncImpl<RenamedOneDirectory> implements RenamedOneDirectoryServiceAsync {

    @Autowired
    public RenamedOneDirectoryServiceAsyncImpl(RenamedOneDirectoryService renamedOneDirectoryService) {
       super(renamedOneDirectoryService);
    }
}
