package org.woehlke.tools.db.dao;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.woehlke.tools.db.JobEvent;

@NoRepositoryBean
public interface JobEventDao<T extends JobEvent> extends PagingAndSortingRepository<T,Long> {
}
