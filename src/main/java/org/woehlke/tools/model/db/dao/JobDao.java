package org.woehlke.tools.model.db.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.woehlke.tools.model.db.Job;

@Component
@Repository
public interface JobDao extends PagingAndSortingRepository<Job,Long> {
}
