package org.woehlke.tools.db.services;

import org.woehlke.tools.db.Job;

public interface JobService {

    Job start(Job p);

    Job finish(Job p);
}
