package org.woehlke.tools.db.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.woehlke.tools.db.JobStep;

@Component
@Repository
public interface JobStepDao  extends PagingAndSortingRepository<JobStep,Long> {
}
