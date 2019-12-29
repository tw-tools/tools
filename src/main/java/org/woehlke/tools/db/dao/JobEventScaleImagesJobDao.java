package org.woehlke.tools.db.dao;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.woehlke.tools.db.JobEventScaleImagesJob;

@Component
@Repository
public interface JobEventScaleImagesJobDao extends JobEventDao<JobEventScaleImagesJob>{
}
