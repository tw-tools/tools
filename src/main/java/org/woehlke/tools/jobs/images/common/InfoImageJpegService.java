package org.woehlke.tools.jobs.images.common;

import org.apache.commons.imaging.formats.jpeg.JpegImageMetadata;

import java.io.File;
import java.util.Map;

public interface InfoImageJpegService {

    String analyseFileContentInformation(String filepath);

    Map<String,String> getFileInfo(String filepath);

    JpegImageMetadata getImageMetadata(File srcFileCopy);

    long getWidth(final JpegImageMetadata jpegMetadata);

    long getLength(final JpegImageMetadata jpegMetadata);
}
