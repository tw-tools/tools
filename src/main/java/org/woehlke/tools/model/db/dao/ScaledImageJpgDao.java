package org.woehlke.tools.model.db.dao;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.woehlke.tools.model.db.common.JobEventDao;
import org.woehlke.tools.model.db.entities.ScaledImageJpg;

@Component
@Repository
public interface ScaledImageJpgDao extends JobEventDao<ScaledImageJpg> {
}
