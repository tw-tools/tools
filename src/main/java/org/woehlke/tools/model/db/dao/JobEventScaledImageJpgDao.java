package org.woehlke.tools.model.db.dao;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.woehlke.tools.model.db.JobEventScaledImageJpg;
import org.woehlke.tools.model.db.common.JobEventDao;

@Component
@Repository
public interface JobEventScaledImageJpgDao extends JobEventDao<JobEventScaledImageJpg> {
}
