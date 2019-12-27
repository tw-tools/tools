package org.woehlke.tools.db.services;

import org.woehlke.tools.db.Renamed;

public interface RenamedService {

    Renamed add(Renamed p);

    Iterable<Renamed> getAll();

    void deleteAll();
}
