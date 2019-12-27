package org.woehlke.tools.db.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.tools.db.Renamed;
import org.woehlke.tools.db.RenamedService;
import org.woehlke.tools.db.dao.RenamedDao;

import static org.springframework.transaction.annotation.Propagation.REQUIRED;
import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;


@Service("renamedService")
public class RenamedServiceImpl implements RenamedService {

    private final RenamedDao renamedDao;

    @Autowired
    public RenamedServiceImpl(@Qualifier("renamedDao") RenamedDao renamedDao) {
        this.renamedDao = renamedDao;
    }

    @Override
    @Transactional(propagation=REQUIRES_NEW)
    public Renamed add(Renamed p) {
        return this.renamedDao.save(p);
    }

    @Override
    @Transactional(propagation=REQUIRED,readOnly=true)
    public Iterable<Renamed> getAll() {
        return this.renamedDao.findAll();
    }

    @Override
    @Transactional(propagation=REQUIRES_NEW)
    public void deleteAll() {
        this.renamedDao.deleteAll();
    }
}
