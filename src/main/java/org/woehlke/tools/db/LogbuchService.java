package org.woehlke.tools.db;

import org.woehlke.tools.db.Logbuch;

public interface LogbuchService {

    void add(Logbuch p);

    Iterable<Logbuch> getAll();

    void deleteAll();
}
