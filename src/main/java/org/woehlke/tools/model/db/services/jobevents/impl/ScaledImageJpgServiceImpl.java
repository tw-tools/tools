package org.woehlke.tools.model.db.services.jobevents.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.woehlke.tools.model.db.dao.jobevents.ScaledImageJpgDao;
import org.woehlke.tools.model.db.entities.jobevents.ScaledImageJpg;
import org.woehlke.tools.model.db.services.impl.JobEventServiceImpl;
import org.woehlke.tools.model.db.services.jobevents.ScaledImageJpgService;

@Service
public class ScaledImageJpgServiceImpl extends JobEventServiceImpl<ScaledImageJpg> implements ScaledImageJpgService {

    @Autowired
    protected ScaledImageJpgServiceImpl(ScaledImageJpgDao jobEventDao) {
        super(jobEventDao);
    }
}
