package org.woehlke.tools.model.db.services.jobevents.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.woehlke.tools.model.db.entities.jobevents.RenameFilesJob;
import org.woehlke.tools.model.db.services.impl.JobEventServiceAsyncImpl;
import org.woehlke.tools.model.db.services.jobevents.RenameFilesJobService;
import org.woehlke.tools.model.db.services.jobevents.RenameFilesJobServiceAsync;

@Service
public class RenameFilesJobServiceAsyncImpl extends JobEventServiceAsyncImpl<RenameFilesJob> implements RenameFilesJobServiceAsync {

    @Autowired
    public RenameFilesJobServiceAsyncImpl(RenameFilesJobService jobEventService) {
        super(jobEventService);
    }
}
