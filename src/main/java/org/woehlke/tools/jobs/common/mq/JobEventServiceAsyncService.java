package org.woehlke.tools.jobs.common.mq;

import org.springframework.scheduling.annotation.Async;
import org.woehlke.tools.model.db.JobEventRenameFilesJob;
import org.woehlke.tools.model.db.JobEventRenamedOneDirectory;
import org.woehlke.tools.model.db.JobEventRenamedOneFile;
import org.woehlke.tools.model.db.JobEventScaleImagesJob;

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
