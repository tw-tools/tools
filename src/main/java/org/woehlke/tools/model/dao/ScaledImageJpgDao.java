package org.woehlke.tools.model.dao;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.woehlke.tools.model.common.JobEventDao;
import org.woehlke.tools.model.entities.ScaledImageJpg;

@Component
@Repository
public interface ScaledImageJpgDao extends JobEventDao<ScaledImageJpg> {
}
