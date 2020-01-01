package org.woehlke.tools.model.services;

import org.woehlke.tools.model.entities.Job;

public interface JobService {

    Job start(Job p);

    Job finish(Job p);

    Job add(Job p);
}
