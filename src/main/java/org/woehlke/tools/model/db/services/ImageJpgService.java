package org.woehlke.tools.model.db.services;

import org.woehlke.tools.model.db.ImageJpg;

public interface ImageJpgService {

    void add(ImageJpg p);

    Iterable<ImageJpg> getAll();

    void deleteAll();
}
