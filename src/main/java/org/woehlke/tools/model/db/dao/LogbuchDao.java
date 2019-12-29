package org.woehlke.tools.model.db.dao;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.woehlke.tools.model.db.entities.Logbuch;

@Deprecated
@Component
@Repository
public interface LogbuchDao extends PagingAndSortingRepository<Logbuch,Long> {
}
