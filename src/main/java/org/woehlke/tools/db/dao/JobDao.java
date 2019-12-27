package org.woehlke.tools.db.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.woehlke.tools.db.Job;

@Repository("jobDao")
public interface JobDao extends PagingAndSortingRepository<Job,Long> {
}
