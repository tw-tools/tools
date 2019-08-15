package org.woehlke.tools.filesystem;

import java.util.Map;

public interface InfoImageJpeg {

    String analyseFileContentInformation(String filepath);

    Map<String,String> getFileInfo(String filepath);
}
