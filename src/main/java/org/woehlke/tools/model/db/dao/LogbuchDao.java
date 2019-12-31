package org.woehlke.tools.model.db.dao;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.woehlke.tools.model.db.common.JobEventDao;
import org.woehlke.tools.model.db.entities.Logbuch;

@Component
@Repository
public interface LogbuchDao extends JobEventDao<Logbuch> {
}
