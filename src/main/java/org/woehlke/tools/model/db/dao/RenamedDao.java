package org.woehlke.tools.model.db.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.woehlke.tools.model.db.entities.Renamed;

@Deprecated
@Component
@Repository
public interface RenamedDao extends PagingAndSortingRepository<Renamed,Long> {
}
