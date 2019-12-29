package org.woehlke.tools.jobs.mq;

import org.springframework.scheduling.annotation.Async;
import org.woehlke.tools.db.JobEventRenameFilesJob;
import org.woehlke.tools.db.JobEventRenamedOneDirectory;
import org.woehlke.tools.db.JobEventRenamedOneFile;
import org.woehlke.tools.db.JobEventScaleImagesJob;

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
