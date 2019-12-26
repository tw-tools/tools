package org.woehlke.tools.db.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.woehlke.tools.db.dao.JpgImageDao;
import org.woehlke.tools.db.entity.JpgImage;
import org.woehlke.tools.db.service.JpgImageService;

@Service
public class JpgImageServiceImpl implements JpgImageService {

    private final JpgImageDao jpgImageDao;

    @Autowired
    public JpgImageServiceImpl(JpgImageDao jpgImageDao) {
        this.jpgImageDao = jpgImageDao;
    }

    @Override
    public void add(JpgImage p) {
        this.jpgImageDao.save(p);
    }

    @Override
    public Iterable<JpgImage> getAll() {
        return this.jpgImageDao.findAll();
    }
}
