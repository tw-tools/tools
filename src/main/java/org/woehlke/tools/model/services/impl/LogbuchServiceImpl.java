package org.woehlke.tools.model.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.woehlke.tools.model.dao.LogbuchDao;
import org.woehlke.tools.model.entities.Logbuch;
import org.woehlke.tools.model.common.impl.JobEventServiceImpl;
import org.woehlke.tools.model.services.LogbuchService;
@Service
public class LogbuchServiceImpl extends JobEventServiceImpl<Logbuch> implements LogbuchService {

    @Autowired
    public LogbuchServiceImpl(LogbuchDao logbuchDao) {
        super(logbuchDao);
    }

}
