package org.woehlke.tools.jobs.images.impl;

import org.apache.commons.imaging.ImageInfo;
import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.common.ImageMetadata;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.woehlke.tools.config.ToolsApplicationProperties;
import org.woehlke.tools.jobs.common.LogbuchQueueService;
import org.woehlke.tools.jobs.images.InfoImagePng;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class InfoImagePngIml implements InfoImagePng {

    private final LogbuchQueueService log;
    private final ToolsApplicationProperties toolsApplicationProperties;

    @Autowired
    public InfoImagePngIml(@Qualifier("jobScaleImagesQueueImpl") LogbuchQueueService log, ToolsApplicationProperties toolsApplicationProperties) {
        this.log = log;
        this.toolsApplicationProperties = toolsApplicationProperties;
    }

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


    private Log logger = LogFactory.getLog(InfoImagePngIml.class);
}
