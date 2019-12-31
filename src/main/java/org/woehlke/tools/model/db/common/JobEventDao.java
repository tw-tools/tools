package org.woehlke.tools.model.db.common;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.woehlke.tools.model.db.common.JobEvent;

@NoRepositoryBean
public interface JobEventDao<T extends JobEvent> extends PagingAndSortingRepository<T,Long> {
}
