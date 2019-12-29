package org.woehlke.tools.model.jobs.images;

import org.woehlke.tools.model.db.JobEventImageJpgInfo;

import java.io.File;
import java.util.List;

public interface ShrinkJpgImage {

    File shrienk(File src);

    List<JobEventImageJpgInfo>  getListJobEventImageJpgInfo();
}
