package org.woehlke.tools.filesystem.impl;

import org.apache.commons.imaging.ImageInfo;
import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.common.ImageMetadata;

import org.woehlke.tools.filesystem.InfoImagePng;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class InfoImagePngIml implements InfoImagePng {

    @Override
    public String analyseFileContentInformation(String filepath) {
        System.out.println("IMAGE_PNG: "+filepath);
        File fileObj = new File(filepath);
        final ImageMetadata metadata;
        final ImageInfo imageInfo;
        try {
            imageInfo = Imaging.getImageInfo(fileObj);
            System.out.println(imageInfo.toString());
            metadata = Imaging.getMetadata(fileObj);
            System.out.println(metadata.toString());
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
