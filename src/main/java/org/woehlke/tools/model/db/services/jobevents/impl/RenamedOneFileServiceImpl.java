package org.woehlke.tools.model.db.services.jobevents.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.woehlke.tools.model.db.dao.jobevents.RenamedOneFileDao;
import org.woehlke.tools.model.db.entities.jobevents.RenamedOneFile;
import org.woehlke.tools.model.db.services.impl.JobEventServiceImpl;
import org.woehlke.tools.model.db.services.jobevents.RenamedOneFileService;

@Service
public class RenamedOneFileServiceImpl extends JobEventServiceImpl<RenamedOneFile> implements RenamedOneFileService {

    @Autowired
    public RenamedOneFileServiceImpl(RenamedOneFileDao renamedOneFileDao) {
        super(renamedOneFileDao);
    }
}
