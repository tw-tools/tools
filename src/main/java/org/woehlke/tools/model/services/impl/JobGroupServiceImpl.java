package org.woehlke.tools.model.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.tools.model.dao.JobGroupDao;
import org.woehlke.tools.model.entities.JobGroup;
import org.woehlke.tools.model.services.JobGroupService;

import java.time.LocalDateTime;

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
        p.setStarted(LocalDateTime.now());
        return jobGroupDao.save(p);
    }

    @Override
    @Transactional(propagation=REQUIRES_NEW)
    public JobGroup finish(JobGroup p) {
        p.setFinished(LocalDateTime.now());
        return jobGroupDao.save(p);
    }

    @Override
    @Transactional(propagation=REQUIRES_NEW)
    public JobGroup add(JobGroup p) {
        return jobGroupDao.save(p);
    }
}
