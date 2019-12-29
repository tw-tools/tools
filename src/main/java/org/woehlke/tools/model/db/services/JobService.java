package org.woehlke.tools.model.db.services;

import org.woehlke.tools.model.db.Job;

public interface JobService {

    Job start(Job p);

    Job finish(Job p);
}
