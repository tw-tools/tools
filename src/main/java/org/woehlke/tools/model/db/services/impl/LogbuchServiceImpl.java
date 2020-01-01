package org.woehlke.tools.model.db.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.woehlke.tools.model.db.dao.LogbuchDao;
import org.woehlke.tools.model.db.entities.Logbuch;
import org.woehlke.tools.model.db.common.impl.JobEventServiceImpl;
import org.woehlke.tools.model.db.services.LogbuchService;
@Service
public class LogbuchServiceImpl extends JobEventServiceImpl<Logbuch> implements LogbuchService {

    @Autowired
    public LogbuchServiceImpl(LogbuchDao logbuchDao) {
        super(logbuchDao);
    }

}
