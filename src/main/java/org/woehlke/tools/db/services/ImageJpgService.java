package org.woehlke.tools.db.services;

import org.woehlke.tools.db.ImageJpg;

public interface ImageJpgService {

    void add(ImageJpg p);

    Iterable<ImageJpg> getAll();

    void deleteAll();
}
