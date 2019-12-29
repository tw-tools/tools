package org.woehlke.tools.model.jobs.rename.mq;

import org.springframework.scheduling.annotation.Async;
import org.woehlke.tools.model.db.Renamed;

public interface JobRenameFilesAsyncService {

    void deleteAll();

    @Async
    Renamed add(Renamed p);
}
