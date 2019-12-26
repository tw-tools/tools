package org.woehlke.tools.db.service;

import org.woehlke.tools.db.entity.JpgImage;

public interface JpgImageService {

    void add(JpgImage p);

    Iterable<JpgImage> getAll();

    void deleteAll();
}
