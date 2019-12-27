package org.woehlke.tools.db.services;

import org.woehlke.tools.db.ImageJpg;

public interface JpgImageService {

    void add(ImageJpg p);

    Iterable<ImageJpg> getAll();

    void deleteAll();
}
