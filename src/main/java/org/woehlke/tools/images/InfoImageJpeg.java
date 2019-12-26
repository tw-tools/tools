package org.woehlke.tools.images;

import java.util.Map;

public interface InfoImageJpeg {

    String analyseFileContentInformation(String filepath);

    Map<String,String> getFileInfo(String filepath);
}
