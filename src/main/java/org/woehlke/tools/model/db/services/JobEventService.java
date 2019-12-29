package org.woehlke.tools.model.db.services;

import org.woehlke.tools.model.db.*;

public interface JobEventService {

    JobEventScaleImagesJob add(JobEventScaleImagesJob p);
    JobEventRenameFilesJob add(JobEventRenameFilesJob p);
    JobEventRenamedOneFile add(JobEventRenamedOneFile p);
    JobEventRenamedOneDirectory add(JobEventRenamedOneDirectory p);
    JobEventScaledImageJpg add(JobEventScaledImageJpg img);
}
