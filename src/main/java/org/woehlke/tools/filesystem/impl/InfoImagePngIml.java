package org.woehlke.tools.filesystem.impl;

import org.apache.commons.imaging.ImageInfo;
import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.common.ImageMetadata;

import org.woehlke.tools.filesystem.InfoImagePng;

import java.io.File;
import java.io.IOException;


public class InfoImagePngIml implements InfoImagePng {



    @Override
    public FileInfoImagePng analyseFileContentInformation(FileProxy file) {
        log.info(FileTypeEnum.IMAGE_PNG +": "+file.getPath());
        File fileObj = new File(file.getPath());
        final ImageMetadata metadata;
        final ImageInfo imageInfo;
        try {
            imageInfo = Imaging.getImageInfo(fileObj);  //.getMetadata(fileObj);
            log.info(imageInfo.toString());
            //metadata = Imaging.getMetadata(fileObj);
            //log.info(metadata.toString());
        } catch (ImageReadException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new FileInfoImagePng(file);
    }

    @Override
    public FileInfo getFileInfo(FileProxy file) {
        log.info(FileTypeEnum.APPLICATION_PDF.getMimeType()+": "+file.getPath());
        return new FileInfo(file);
    }
}
