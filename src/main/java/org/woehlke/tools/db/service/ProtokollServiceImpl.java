package org.woehlke.tools.db.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.tools.db.dao.ProtokollDao;
import org.woehlke.tools.db.entity.Protokoll;

import static org.springframework.transaction.annotation.Propagation.REQUIRED;
import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;


@Service
public class ProtokollServiceImpl implements ProtokollService {

    private  final ProtokollDao protokollDao;

    @Autowired
    public ProtokollServiceImpl(ProtokollDao protokollDao) {
        this.protokollDao = protokollDao;
    }

    @Override
    @Transactional(propagation=REQUIRES_NEW)
    public void add(Protokoll p) {
        protokollDao.save(p);
    }

    @Override
    @Transactional(propagation=REQUIRED,readOnly=true)
    public Iterable<Protokoll> getAll() {
        return protokollDao.findAll();
    }
}
