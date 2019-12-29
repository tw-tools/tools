package org.woehlke.tools.model.db.common;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

@NoRepositoryBean
public interface JobEventDao<T extends JobEvent> extends PagingAndSortingRepository<T,Long> {
}
