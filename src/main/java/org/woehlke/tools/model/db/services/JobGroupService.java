package org.woehlke.tools.model.db.services;

import org.woehlke.tools.model.db.entities.JobGroup;

public interface JobGroupService {

    JobGroup start(JobGroup p);

    JobGroup finish(JobGroup p);

    JobGroup add(JobGroup jobGroup);
}
