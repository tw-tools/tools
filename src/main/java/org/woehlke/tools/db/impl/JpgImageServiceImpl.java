package org.woehlke.tools.db.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.tools.db.dao.JpgImageDao;
import org.woehlke.tools.db.JpgImage;
import org.woehlke.tools.db.JpgImageService;

import static org.springframework.transaction.annotation.Propagation.REQUIRED;
import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

@Service
public class JpgImageServiceImpl implements JpgImageService {

    private final JpgImageDao jpgImageDao;

    @Autowired
    public JpgImageServiceImpl(JpgImageDao jpgImageDao) {
        this.jpgImageDao = jpgImageDao;
    }

    @Override
    @Transactional(propagation=REQUIRES_NEW)
    public void add(JpgImage p) {
        this.jpgImageDao.save(p);
    }

    @Override
    @Transactional(propagation=REQUIRED,readOnly=true)
    public Iterable<JpgImage> getAll() {
        return this.jpgImageDao.findAll();
    }

    @Override
    @Transactional(propagation=REQUIRES_NEW)
    public void deleteAll() {
        this.jpgImageDao.deleteAll();
    }
}
