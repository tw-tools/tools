package org.woehlke.tools.db;

public interface RenamedService {

    Renamed add(Renamed p);

    Iterable<Renamed> getAll();

    void deleteAll();
}
