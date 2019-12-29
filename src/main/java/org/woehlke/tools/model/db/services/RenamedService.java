package org.woehlke.tools.model.db.services;

import org.woehlke.tools.model.db.Renamed;

@Deprecated
public interface RenamedService {

    @Deprecated
    Renamed add(Renamed p);

    @Deprecated
    Iterable<Renamed> getAll();

    @Deprecated
    void deleteAll();
}
