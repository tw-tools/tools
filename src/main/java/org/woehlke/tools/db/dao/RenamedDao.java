package org.woehlke.tools.db.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.woehlke.tools.db.Renamed;

@Component
@Repository
public interface RenamedDao extends PagingAndSortingRepository<Renamed,Long> {
}
