package org.woehlke.tools.db.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.tools.db.dao.LogbuchDao;
import org.woehlke.tools.db.entity.Logbuch;
import org.woehlke.tools.db.service.ProtokollService;

import static org.springframework.transaction.annotation.Propagation.REQUIRED;
import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;


@Service
public class ProtokollServiceImpl implements ProtokollService {

    private final LogbuchDao logbuchDao;

    @Autowired
    public ProtokollServiceImpl(@Qualifier("logbuchDao") LogbuchDao logbuchDao) {
        this.logbuchDao = logbuchDao;
    }

    @Override
    @Transactional(propagation=REQUIRES_NEW)
    public void add(Logbuch p) {
        logbuchDao.save(p);
    }

    @Override
    @Transactional(propagation=REQUIRED,readOnly=true)
    public Iterable<Logbuch> getAll() {
        return logbuchDao.findAll();
    }
}
