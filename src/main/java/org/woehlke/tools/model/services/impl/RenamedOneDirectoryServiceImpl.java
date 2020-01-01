package org.woehlke.tools.model.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.woehlke.tools.model.dao.RenamedOneDirectoryDao;
import org.woehlke.tools.model.entities.RenamedOneDirectory;
import org.woehlke.tools.model.common.impl.JobEventServiceImpl;
import org.woehlke.tools.model.services.RenamedOneDirectoryService;

@Service
public class RenamedOneDirectoryServiceImpl extends JobEventServiceImpl<RenamedOneDirectory> implements RenamedOneDirectoryService {

    @Autowired
    public RenamedOneDirectoryServiceImpl(RenamedOneDirectoryDao renamedOneDirectoryDao) {
        super(renamedOneDirectoryDao);
    }
}
