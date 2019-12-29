package org.woehlke.tools.model.db.services.jobevents.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.woehlke.tools.model.db.dao.jobevents.RenamedOneDirectoryDao;
import org.woehlke.tools.model.db.entities.jobevents.RenamedOneDirectory;
import org.woehlke.tools.model.db.services.impl.JobEventServiceImpl;
import org.woehlke.tools.model.db.services.jobevents.RenamedOneDirectoryService;

@Service
public class RenamedOneDirectoryServiceImpl extends JobEventServiceImpl<RenamedOneDirectory> implements RenamedOneDirectoryService {

    @Autowired
    public RenamedOneDirectoryServiceImpl(RenamedOneDirectoryDao renamedOneDirectoryDao) {
        super(renamedOneDirectoryDao);
    }
}
