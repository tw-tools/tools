package org.woehlke.tools.model.db.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.woehlke.tools.model.db.dao.ScaledImageJpgDao;
import org.woehlke.tools.model.db.entities.ScaledImageJpg;
import org.woehlke.tools.model.db.common.JobEventServiceImpl;
import org.woehlke.tools.model.db.services.ScaledImageJpgService;

@Service
public class ScaledImageJpgServiceImpl extends JobEventServiceImpl<ScaledImageJpg> implements ScaledImageJpgService {

    @Autowired
    protected ScaledImageJpgServiceImpl(ScaledImageJpgDao jobEventDao) {
        super(jobEventDao);
    }
}
