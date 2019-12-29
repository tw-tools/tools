package org.woehlke.tools.db.dao;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.woehlke.tools.db.JobEventRenameFilesJob;

@Component
@Repository
public interface JobEventRenameFilesJobDao extends JobEventDao<JobEventRenameFilesJob> {
}
