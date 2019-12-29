package org.woehlke.tools.model.db.services;

import org.woehlke.tools.model.db.Logbuch;

@Deprecated
public interface LogbuchService {

    @Deprecated
    Logbuch add(Logbuch p);

    @Deprecated
    Iterable<Logbuch> getAll();

    @Deprecated
    void deleteAll();

}
