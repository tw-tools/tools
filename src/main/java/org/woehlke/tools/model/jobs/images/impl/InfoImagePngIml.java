package org.woehlke.tools.model.jobs.images.impl;

import org.apache.commons.imaging.ImageInfo;
import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.common.ImageMetadata;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.woehlke.tools.model.mq.ImagesInfoQueue;
import org.woehlke.tools.model.jobs.images.InfoImagePng;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class InfoImagePngIml implements InfoImagePng {

    private final ImagesInfoQueue imagesInfoQueue;

    @Autowired
    public InfoImagePngIml(
        final ImagesInfoQueue imagesInfoQueue
    ) {
        this.imagesInfoQueue = imagesInfoQueue;
    }

    @Override
    public String analyseFileContentInformation(String filepath) {
        logger.info("IMAGE_PNG: "+filepath);
        File fileObj = new File(filepath);
        final ImageMetadata metadata;
        final ImageInfo imageInfo;
        try {
            imageInfo = Imaging.getImageInfo(fileObj);
            logger.info(imageInfo.toString());
            metadata = Imaging.getMetadata(fileObj);
            logger.info(metadata.toString());
        } catch (ImageReadException | IOException e) {
            logger.info(e.getMessage());
        }
        return "";
    }

    @Override
    public Map<String, String> getFileInfo(String filepath) {
        Map<String,String> info = new HashMap<String,String>();
        return info;
    }


    private Log logger = LogFactory.getLog(InfoImagePngIml.class);
}
