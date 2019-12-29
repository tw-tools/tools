package org.woehlke.tools.db.services;

import org.woehlke.tools.db.JobEventRenameFilesJob;
import org.woehlke.tools.db.JobEventRenamedOneDirectory;
import org.woehlke.tools.db.JobEventRenamedOneFile;
import org.woehlke.tools.db.JobEventScaleImagesJob;

public interface JobEventService {

    JobEventScaleImagesJob add(JobEventScaleImagesJob p);
    JobEventRenameFilesJob add(JobEventRenameFilesJob p);
    JobEventRenamedOneFile add(JobEventRenamedOneFile p);
    JobEventRenamedOneDirectory add(JobEventRenamedOneDirectory p);
}
