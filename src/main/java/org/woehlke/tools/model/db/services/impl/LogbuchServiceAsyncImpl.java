package org.woehlke.tools.model.db.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.woehlke.tools.model.db.entities.Logbuch;
import org.woehlke.tools.model.db.common.impl.JobEventServiceAsyncImpl;
import org.woehlke.tools.model.db.services.LogbuchService;
import org.woehlke.tools.model.db.services.LogbuchServiceAsync;

@Service
public class LogbuchServiceAsyncImpl extends JobEventServiceAsyncImpl<Logbuch> implements LogbuchServiceAsync {

    @Autowired
    public LogbuchServiceAsyncImpl(LogbuchService logbuchService) {
        super(logbuchService);
    }
}
