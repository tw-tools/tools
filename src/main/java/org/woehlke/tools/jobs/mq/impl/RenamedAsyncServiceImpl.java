package org.woehlke.tools.jobs.mq.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.woehlke.tools.db.Renamed;
import org.woehlke.tools.db.RenamedService;
import org.woehlke.tools.jobs.mq.RenamedAsyncService;

@Service("renamedAsyncService")
public class RenamedAsyncServiceImpl implements RenamedAsyncService {

    private final RenamedService renamedService;

    @Autowired
    public RenamedAsyncServiceImpl(RenamedService renamedService) {
        this.renamedService = renamedService;
    }

    @Override
    public void deleteAll() {
        this.renamedService.deleteAll();
    }

    @Async
    @Override
    public Renamed add(Renamed p) {
        return renamedService.add(p);
    }
}
