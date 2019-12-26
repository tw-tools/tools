package org.woehlke.tools.db.service;

import org.woehlke.tools.db.entity.Logbuch;

public interface LogbuchService {

    void add(Logbuch p);

    Iterable<Logbuch> getAll();

    void deleteAll();
}
