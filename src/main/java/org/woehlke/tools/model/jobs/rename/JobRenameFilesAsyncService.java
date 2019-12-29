package org.woehlke.tools.model.jobs.rename;

import org.springframework.scheduling.annotation.Async;
import org.woehlke.tools.model.db.entities.Renamed;

public interface JobRenameFilesAsyncService {

    void deleteAll();

    @Async
    Renamed add(Renamed p);
}
