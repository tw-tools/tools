package org.woehlke.tools.jobs.images.info;


import java.util.Map;

public interface InfoImagePngService {

    String analyseFileContentInformation(String filepath);

    Map<String,String> getFileInfo(String filepath);
}
