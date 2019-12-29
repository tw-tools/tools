package org.woehlke.tools.model.db.dao;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.woehlke.tools.model.db.JobEventRenameFilesJob;
import org.woehlke.tools.model.db.common.JobEventDao;

@Component
@Repository
public interface JobEventRenameFilesJobDao extends JobEventDao<JobEventRenameFilesJob> {
}
