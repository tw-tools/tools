package org.woehlke.tools.db.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.tools.db.dao.LogbuchDao;
import org.woehlke.tools.db.Logbuch;
import org.woehlke.tools.db.LogbuchService;

import static org.springframework.transaction.annotation.Propagation.REQUIRED;
import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;


@Service
public class LogbuchServiceImpl implements LogbuchService {

    private final LogbuchDao logbuchDao;

    @Autowired
    public LogbuchServiceImpl(@Qualifier("logbuchDao") LogbuchDao logbuchDao) {
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

    @Override
    @Transactional(propagation=REQUIRES_NEW)
    public void deleteAll() {
        logbuchDao.deleteAll();
    }
}