package org.woehlke.tools.filesystem.impl;

import org.apache.commons.imaging.ImageInfo;
import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.common.ImageMetadata;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.woehlke.tools.filesystem.InfoImagePng;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class InfoImagePngIml implements InfoImagePng {

    private static final Logger log = LoggerFactory.getLogger(InfoImagePngIml.class);

    @Override
    public String analyseFileContentInformation(String filepath) {
        log.info("IMAGE_PNG: "+filepath);
        File fileObj = new File(filepath);
        final ImageMetadata metadata;
        final ImageInfo imageInfo;
        try {
            imageInfo = Imaging.getImageInfo(fileObj);
            log.info(imageInfo.toString());
            metadata = Imaging.getMetadata(fileObj);
            log.info(metadata.toString());
        } catch (ImageReadException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public Map<String, String> getFileInfo(String filepath) {
        Map<String,String> info = new HashMap<String,String>();
        return info;
    }

}
