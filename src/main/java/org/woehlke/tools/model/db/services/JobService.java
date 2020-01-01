package org.woehlke.tools.model.db.services;

import org.woehlke.tools.model.db.entities.Job;

public interface JobService {

    Job start(Job p);

    Job finish(Job p);

    Job add(Job p);
}
