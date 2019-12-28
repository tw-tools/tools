package org.woehlke.tools.db.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.tools.config.ToolsApplicationProperties;
import org.woehlke.tools.db.dao.LogbuchDao;
import org.woehlke.tools.db.Logbuch;
import org.woehlke.tools.db.services.LogbuchService;

import static org.springframework.transaction.annotation.Propagation.REQUIRED;
import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;


@Service("logbuchService")
public class LogbuchServiceImpl implements LogbuchService {

    private final LogbuchDao logbuchDao;
    private final ToolsApplicationProperties toolsApplicationProperties;

    @Autowired
    public LogbuchServiceImpl(LogbuchDao logbuchDao, ToolsApplicationProperties toolsApplicationProperties) {
        this.logbuchDao = logbuchDao;
        this.toolsApplicationProperties = toolsApplicationProperties;
    }

    @Override
    @Transactional(propagation=REQUIRES_NEW)
    public Logbuch add(Logbuch p) {
        return logbuchDao.save(p);
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
