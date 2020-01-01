package org.woehlke.tools.model.dao;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.woehlke.tools.model.entities.RenamedOneDirectory;
import org.woehlke.tools.model.common.JobEventDao;

@Component
@Repository
public interface RenamedOneDirectoryDao extends JobEventDao<RenamedOneDirectory> {
}
