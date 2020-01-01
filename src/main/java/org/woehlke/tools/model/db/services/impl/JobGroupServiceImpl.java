package org.woehlke.tools.model.db.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.tools.model.db.dao.JobDao;
import org.woehlke.tools.model.db.dao.JobGroupDao;
import org.woehlke.tools.model.db.entities.Job;
import org.woehlke.tools.model.db.entities.JobGroup;
import org.woehlke.tools.model.db.services.JobGroupService;

import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

@Service
public class JobGroupServiceImpl implements JobGroupService {

    private final JobGroupDao jobGroupDao;
    private final JobDao jobDao;

    @Autowired
    public JobGroupServiceImpl(JobGroupDao jobGroupDao, JobDao jobDao) {
        this.jobGroupDao = jobGroupDao;
        this.jobDao = jobDao;
    }

    @Override
    @Transactional(propagation=REQUIRES_NEW)
    public JobGroup start(JobGroup p) {
        Set<Job> jobSet = p.getJobSet();
        Iterable<Job> result = jobDao.saveAll(jobSet);
        jobSet = Collections.synchronizedSortedSet(new TreeSet<>());
        for (Job job:result){
            jobSet.add(job);
        }
        p.setJobSet(jobSet);
        return jobGroupDao.save(p);
    }

    @Override
    @Transactional(propagation=REQUIRES_NEW)
    public JobGroup finish(JobGroup p) {
        return jobGroupDao.save(p);
    }
}
