package org.woehlke.tools.images;

import org.woehlke.tools.images.model.JpgImage;

import java.io.File;
import java.util.List;

public interface ShrinkJpgImage {

    File shrienk(File src);

    List<JpgImage> getListJpgImage();
}
