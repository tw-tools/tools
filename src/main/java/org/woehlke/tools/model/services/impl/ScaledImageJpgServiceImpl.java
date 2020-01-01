package org.woehlke.tools.model.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.woehlke.tools.model.dao.ScaledImageJpgDao;
import org.woehlke.tools.model.entities.ScaledImageJpg;
import org.woehlke.tools.model.common.impl.JobEventServiceImpl;
import org.woehlke.tools.model.services.ScaledImageJpgService;

@Service
public class ScaledImageJpgServiceImpl extends JobEventServiceImpl<ScaledImageJpg> implements ScaledImageJpgService {

    @Autowired
    protected ScaledImageJpgServiceImpl(ScaledImageJpgDao jobEventDao) {
        super(jobEventDao);
    }
}
