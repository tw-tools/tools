package org.woehlke.tools.model.db.services;

import org.woehlke.tools.model.db.entities.JobEvent;

public interface JobEventService<T extends JobEvent>{

    T add(T p);

}
