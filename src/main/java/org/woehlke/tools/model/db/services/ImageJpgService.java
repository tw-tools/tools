package org.woehlke.tools.model.db.services;

import org.woehlke.tools.model.db.ImageJpg;

@Deprecated
public interface ImageJpgService {

    @Deprecated
    void add(ImageJpg p);

    @Deprecated
    Iterable<ImageJpg> getAll();

    @Deprecated
    void deleteAll();
}
