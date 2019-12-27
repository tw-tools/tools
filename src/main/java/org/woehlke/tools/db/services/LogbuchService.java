package org.woehlke.tools.db.services;

import org.woehlke.tools.db.Logbuch;

public interface LogbuchService {

    Logbuch add(Logbuch p);

    Iterable<Logbuch> getAll();

    void deleteAll();

}
