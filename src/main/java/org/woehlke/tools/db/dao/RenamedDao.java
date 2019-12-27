package org.woehlke.tools.db.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.woehlke.tools.db.Renamed;

@Repository("renamedDao")
public interface RenamedDao extends PagingAndSortingRepository<Renamed,Long> {
}
