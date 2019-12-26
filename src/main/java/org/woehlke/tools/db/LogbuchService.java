package org.woehlke.tools.db;

public interface LogbuchService {

    Logbuch add(Logbuch p);

    Iterable<Logbuch> getAll();

    void deleteAll();
}
