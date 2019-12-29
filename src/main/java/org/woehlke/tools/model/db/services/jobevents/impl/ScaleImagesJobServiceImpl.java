package org.woehlke.tools.model.db.services.jobevents.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.woehlke.tools.model.db.dao.jobevents.ScaleImagesJobDao;
import org.woehlke.tools.model.db.entities.jobevents.ScaleImagesJob;
import org.woehlke.tools.model.db.services.impl.JobEventServiceImpl;
import org.woehlke.tools.model.db.services.jobevents.ScaleImagesJobService;

@Service
public class ScaleImagesJobServiceImpl extends JobEventServiceImpl<ScaleImagesJob> implements ScaleImagesJobService {

    @Autowired
    protected ScaleImagesJobServiceImpl(ScaleImagesJobDao jobEventDao) {
        super(jobEventDao);
    }
}
