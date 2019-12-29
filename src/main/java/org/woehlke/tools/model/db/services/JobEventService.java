package org.woehlke.tools.model.db.services;

import org.woehlke.tools.model.db.JobEventRenameFilesJob;
import org.woehlke.tools.model.db.JobEventRenamedOneDirectory;
import org.woehlke.tools.model.db.JobEventRenamedOneFile;
import org.woehlke.tools.model.db.JobEventScaleImagesJob;

public interface JobEventService {

    JobEventScaleImagesJob add(JobEventScaleImagesJob p);
    JobEventRenameFilesJob add(JobEventRenameFilesJob p);
    JobEventRenamedOneFile add(JobEventRenamedOneFile p);
    JobEventRenamedOneDirectory add(JobEventRenamedOneDirectory p);
}
