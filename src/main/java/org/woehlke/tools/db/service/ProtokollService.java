package org.woehlke.tools.db.service;

import org.woehlke.tools.db.entity.Logbuch;

public interface ProtokollService {

    void add(Logbuch p);

    Iterable<Logbuch> getAll();
}
