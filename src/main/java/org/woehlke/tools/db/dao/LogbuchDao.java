package org.woehlke.tools.db.dao;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.woehlke.tools.db.Logbuch;

@Component
@Repository
public interface LogbuchDao extends PagingAndSortingRepository<Logbuch,Long> {
}
