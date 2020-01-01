package org.woehlke.tools.model.db.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.woehlke.tools.model.db.dao.RenamedOneFileDao;
import org.woehlke.tools.model.db.entities.RenamedOneFile;
import org.woehlke.tools.model.db.common.impl.JobEventServiceImpl;
import org.woehlke.tools.model.db.services.RenamedOneFileService;

@Service
public class RenamedOneFileServiceImpl extends JobEventServiceImpl<RenamedOneFile> implements RenamedOneFileService {

    @Autowired
    public RenamedOneFileServiceImpl(RenamedOneFileDao renamedOneFileDao) {
        super(renamedOneFileDao);
    }
}
