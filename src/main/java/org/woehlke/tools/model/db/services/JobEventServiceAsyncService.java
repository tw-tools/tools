package org.woehlke.tools.model.db.services;

import org.springframework.scheduling.annotation.Async;
import org.woehlke.tools.model.db.entities.JobEventRenameFilesJob;
import org.woehlke.tools.model.db.entities.JobEventRenamedOneDirectory;
import org.woehlke.tools.model.db.entities.JobEventRenamedOneFile;
import org.woehlke.tools.model.db.entities.JobEventScaleImagesJob;

public interface JobEventServiceAsyncService {

    @Async
    JobEventScaleImagesJob add(JobEventScaleImagesJob p);

    @Async
    JobEventRenameFilesJob add(JobEventRenameFilesJob p);

    @Async
    JobEventRenamedOneFile add(JobEventRenamedOneFile p);

    @Async
    JobEventRenamedOneDirectory add(JobEventRenamedOneDirectory p);

}
