package org.woehlke.tools.jobs.images.common;


import java.util.Map;

public interface InfoImagePngService {

    String analyseFileContentInformation(String filepath);

    Map<String,String> getFileInfo(String filepath);
}
