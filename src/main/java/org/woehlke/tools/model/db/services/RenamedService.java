package org.woehlke.tools.model.db.services;

import org.woehlke.tools.model.db.Renamed;

public interface RenamedService {

    Renamed add(Renamed p);

    Iterable<Renamed> getAll();

    void deleteAll();
}
