package org.woehlke.tools.model.services;

import org.woehlke.tools.model.entities.JobGroup;

public interface JobGroupService {

    JobGroup start(JobGroup p);

    JobGroup finish(JobGroup p);

    JobGroup add(JobGroup jobGroup);
}
