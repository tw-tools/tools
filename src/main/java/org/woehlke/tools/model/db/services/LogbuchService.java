package org.woehlke.tools.model.db.services;

import org.woehlke.tools.model.db.Logbuch;

public interface LogbuchService {

    Logbuch add(Logbuch p);

    Iterable<Logbuch> getAll();

    void deleteAll();

}
