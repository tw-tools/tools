package org.woehlke.tools.model.common;

public interface JobEventService<T extends JobEvent>{

    T add(T p);

}
