package org.woehlke.tools.jobs.images;

import org.woehlke.tools.db.ImageJpg;

import java.io.File;
import java.util.List;

public interface ShrinkJpgImage {

    File shrienk(File src);

    List<ImageJpg> getListImageJpg();
}
