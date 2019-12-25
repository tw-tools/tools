package org.woehlke.tools.db.dao;


import org.springframework.stereotype.Repository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.woehlke.tools.db.entity.Logbuch;

@Repository("logbuchDao")
public interface LogbuchDao extends PagingAndSortingRepository<Logbuch,Long> {
}
