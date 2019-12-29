package org.woehlke.tools.model.jobs.rename.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.woehlke.tools.config.application.ToolsApplicationProperties;
import org.woehlke.tools.model.db.entities.Renamed;
import org.woehlke.tools.model.db.services.RenamedService;
import org.woehlke.tools.model.jobs.rename.JobRenameFilesAsyncService;

@Service("renamedAsyncService")
public class JobRenameFilesAsyncServiceImpl implements JobRenameFilesAsyncService {

    private final RenamedService renamedService;
    private final ToolsApplicationProperties properties;

    @Autowired
    public JobRenameFilesAsyncServiceImpl(RenamedService renamedService, ToolsApplicationProperties properties) {
        this.renamedService = renamedService;
        this.properties = properties;
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
