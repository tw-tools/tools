package org.woehlke.tools.model.db.common;

import org.woehlke.tools.model.db.common.JobEvent;

public interface JobEventService<T extends JobEvent>{

    T add(T p);

}
