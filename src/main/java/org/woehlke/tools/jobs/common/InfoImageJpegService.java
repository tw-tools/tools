package org.woehlke.tools.jobs.common;

import java.util.Map;

public interface InfoImageJpegService {

    String analyseFileContentInformation(String filepath);

    Map<String,String> getFileInfo(String filepath);
}
