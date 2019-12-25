package org.woehlke.tools.db.service;

import org.woehlke.tools.db.entity.Protokoll;

public interface ProtokollService {

    void add(Protokoll p);

    Iterable<Protokoll> getAll();
}
