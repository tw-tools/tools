package org.woehlke.tools.model.db.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.tools.model.db.dao.JobGroupDao;
import org.woehlke.tools.model.db.entities.JobGroup;
import org.woehlke.tools.model.db.services.JobGroupService;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

@Service
public class JobGroupServiceImpl implements JobGroupService {

    private final JobGroupDao jobGroupDao;

    @Autowired
    public JobGroupServiceImpl(JobGroupDao jobGroupDao) {
        this.jobGroupDao = jobGroupDao;
    }

    @Override
    @Transactional(propagation=REQUIRES_NEW)
    public JobGroup start(JobGroup p) {
        return jobGroupDao.save(p);
    }

    @Override
    @Transactional(propagation=REQUIRES_NEW)
    public JobGroup finish(JobGroup p) {
        return jobGroupDao.save(p);
    }
}
