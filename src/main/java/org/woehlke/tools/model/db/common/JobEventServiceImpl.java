package org.woehlke.tools.model.db.common;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;


@NoRepositoryBean
public abstract class JobEventServiceImpl<T extends JobEvent> implements JobEventService<T> {

    private final JobEventDao<T> jobEventDao;

    protected JobEventServiceImpl(JobEventDao<T> jobEventDao) {
        this.jobEventDao = jobEventDao;
    }

    @Override
    @Transactional(propagation=REQUIRES_NEW)
    public T add(T p) {
        return jobEventDao.save(p);
    }
}
