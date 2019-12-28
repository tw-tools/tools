package org.woehlke.tools.db.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.tools.config.ToolsApplicationProperties;
import org.woehlke.tools.db.Renamed;
import org.woehlke.tools.db.services.RenamedService;
import org.woehlke.tools.db.dao.RenamedDao;

import static org.springframework.transaction.annotation.Propagation.REQUIRED;
import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;


@Service("renamedService")
public class RenamedServiceImpl implements RenamedService {

    private final RenamedDao renamedDao;
    private final ToolsApplicationProperties toolsApplicationProperties;

    @Autowired
    public RenamedServiceImpl(RenamedDao renamedDao, ToolsApplicationProperties toolsApplicationProperties) {
        this.renamedDao = renamedDao;
        this.toolsApplicationProperties = toolsApplicationProperties;
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
