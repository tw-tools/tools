package org.woehlke.tools.db.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.tools.db.Job;
import org.woehlke.tools.db.services.JobService;
import org.woehlke.tools.db.dao.JobDao;

import java.time.LocalDateTime;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;


@Service
public class JobServiceImpl implements JobService {

    private final JobDao jobDao;

    @Autowired
    public JobServiceImpl(JobDao jobDao) {
        this.jobDao = jobDao;
    }

    @Override
    @Transactional(propagation=REQUIRES_NEW)
    public Job start(Job p) {
        LocalDateTime now = LocalDateTime.now();
        p.setStarted(now);
        return this.jobDao.save(p);
    }

    @Override
    @Transactional(propagation=REQUIRES_NEW)
    public Job finish(Job p) {
        LocalDateTime now = LocalDateTime.now();
        p.setFinished(now);
        return this.jobDao.save(p);
    }
}
