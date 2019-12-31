package org.woehlke.tools.model.db.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.woehlke.tools.model.db.dao.RenamedOneDirectoryDao;
import org.woehlke.tools.model.db.entities.RenamedOneDirectory;
import org.woehlke.tools.model.db.common.JobEventServiceImpl;
import org.woehlke.tools.model.db.services.RenamedOneDirectoryService;

@Service
public class RenamedOneDirectoryServiceImpl extends JobEventServiceImpl<RenamedOneDirectory> implements RenamedOneDirectoryService {

    @Autowired
    public RenamedOneDirectoryServiceImpl(RenamedOneDirectoryDao renamedOneDirectoryDao) {
        super(renamedOneDirectoryDao);
    }
}
