package org.woehlke.tools.model.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.woehlke.tools.model.entities.Logbuch;
import org.woehlke.tools.model.common.impl.JobEventServiceAsyncImpl;
import org.woehlke.tools.model.services.LogbuchService;
import org.woehlke.tools.model.services.LogbuchServiceAsync;

@Service
public class LogbuchServiceAsyncImpl extends JobEventServiceAsyncImpl<Logbuch> implements LogbuchServiceAsync {

    @Autowired
    public LogbuchServiceAsyncImpl(LogbuchService logbuchService) {
        super(logbuchService);
    }
}
