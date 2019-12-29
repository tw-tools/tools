package org.woehlke.tools.db.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.tools.db.JobStep;
import org.woehlke.tools.db.dao.JobStepDao;
import org.woehlke.tools.db.services.JobStepService;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

@Service
public class JobStepServiceImpl implements JobStepService {

    private final JobStepDao jobStepDao;

    @Autowired
    public JobStepServiceImpl(JobStepDao jobStepDao) {
        this.jobStepDao = jobStepDao;
    }

    @Override
    @Transactional(propagation=REQUIRES_NEW)
    public JobStep add(JobStep p) {
        return this.jobStepDao.save(p);
    }
}
