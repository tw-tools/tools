package org.woehlke.tools.model.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.woehlke.tools.model.dao.RenamedOneFileDao;
import org.woehlke.tools.model.entities.RenamedOneFile;
import org.woehlke.tools.model.common.impl.JobEventServiceImpl;
import org.woehlke.tools.model.services.RenamedOneFileService;

@Service
public class RenamedOneFileServiceImpl extends JobEventServiceImpl<RenamedOneFile> implements RenamedOneFileService {

    @Autowired
    public RenamedOneFileServiceImpl(RenamedOneFileDao renamedOneFileDao) {
        super(renamedOneFileDao);
    }
}
