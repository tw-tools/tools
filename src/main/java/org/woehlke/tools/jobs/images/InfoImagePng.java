package org.woehlke.tools.jobs.images;


import java.util.Map;

public interface InfoImagePng {

    String analyseFileContentInformation(String filepath);

    Map<String,String> getFileInfo(String filepath);
}