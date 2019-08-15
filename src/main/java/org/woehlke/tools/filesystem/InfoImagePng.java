package org.woehlke.tools.filesystem;


import java.util.Map;

public interface InfoImagePng {

    String analyseFileContentInformation(String filepath);

    Map<String,String> getFileInfo(String filepath);
}
