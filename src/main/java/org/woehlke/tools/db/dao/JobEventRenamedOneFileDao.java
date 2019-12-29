package org.woehlke.tools.db.dao;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.woehlke.tools.db.JobEventRenamedOneFile;

@Component
@Repository
public interface JobEventRenamedOneFileDao extends JobEventDao<JobEventRenamedOneFile> {
}
