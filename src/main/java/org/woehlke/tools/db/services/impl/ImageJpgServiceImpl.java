package org.woehlke.tools.db.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.tools.db.dao.ImageJpgDao;
import org.woehlke.tools.db.ImageJpg;
import org.woehlke.tools.db.services.ImageJpgService;

import static org.springframework.transaction.annotation.Propagation.REQUIRED;
import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

@Service
public class ImageJpgServiceImpl implements ImageJpgService {

    private final ImageJpgDao imageJpgDao;

    @Autowired
    public ImageJpgServiceImpl(ImageJpgDao imageJpgDao) {
        this.imageJpgDao = imageJpgDao;
    }

    @Override
    @Transactional(propagation=REQUIRES_NEW)
    public void add(ImageJpg p) {
        this.imageJpgDao.save(p);
    }

    @Override
    @Transactional(propagation=REQUIRED,readOnly=true)
    public Iterable<ImageJpg> getAll() {
        return this.imageJpgDao.findAll();
    }

    @Override
    @Transactional(propagation=REQUIRES_NEW)
    public void deleteAll() {
        this.imageJpgDao.deleteAll();
    }
}
