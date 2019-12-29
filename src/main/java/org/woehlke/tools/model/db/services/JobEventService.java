package org.woehlke.tools.model.db.services;

import org.woehlke.tools.model.db.entities.*;

public interface JobEventService {

    JobEventScaleImagesJob add(JobEventScaleImagesJob p);
    JobEventRenameFilesJob add(JobEventRenameFilesJob p);
    JobEventRenamedOneFile add(JobEventRenamedOneFile p);
    JobEventRenamedOneDirectory add(JobEventRenamedOneDirectory p);
    JobEventScaledImageJpg add(JobEventScaledImageJpg img);
}
