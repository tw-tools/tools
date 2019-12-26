package org.woehlke.tools.db;

import org.woehlke.tools.db.JpgImage;

public interface JpgImageService {

    void add(JpgImage p);

    Iterable<JpgImage> getAll();

    void deleteAll();
}
