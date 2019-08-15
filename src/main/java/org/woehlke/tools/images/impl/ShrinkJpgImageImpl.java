package org.woehlke.tools.images.impl;

import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.common.ImageMetadata;
import org.apache.commons.imaging.formats.jpeg.JpegImageMetadata;
import org.apache.commons.imaging.formats.tiff.TiffField;
import org.apache.commons.imaging.formats.tiff.constants.ExifTagConstants;
import org.woehlke.tools.images.ShrinkJpgImage;
import org.woehlke.tools.images.model.JpgImage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ShrinkJpgImageImpl implements ShrinkJpgImage {

    private static final Logger log = Logger.getLogger(ShrinkJpgImageImpl.class.getName());

    private List<JpgImage> listJpgImage = new ArrayList<>();

    @Override
    public File shrienk(File srcFile) {
        log.info("JPEG : "+srcFile.getAbsolutePath());
        File fileObj = srcFile;
        final ImageMetadata metadata;
        try {
            metadata = Imaging.getMetadata(fileObj);
            //log.info(metadata.toString());
            if (metadata instanceof JpegImageMetadata) {
                final JpegImageMetadata jpegMetadata = (JpegImageMetadata) metadata;
                long wdith=0L;
                long legth=0L;
                try {
                    final TiffField fieldWidth = jpegMetadata.findEXIFValueWithExactMatch(ExifTagConstants.EXIF_TAG_EXIF_IMAGE_WIDTH);
                    if (fieldWidth != null) {
                        log.info("tagInfo - fieldWidth " + fieldWidth.getValueDescription());
                        wdith = Long.getLong(fieldWidth.getValueDescription());
                    }
                    final TiffField fieldLength = jpegMetadata.findEXIFValueWithExactMatch(ExifTagConstants.EXIF_TAG_EXIF_IMAGE_LENGTH);
                    if (fieldLength != null) {
                        log.info("tagInfo - fieldLength " + fieldLength.getValueDescription());
                        legth = Long.getLong(fieldWidth.getValueDescription());
                    }
                } catch (NullPointerException e){ }
                if(legth>0 && wdith>0){
                    JpgImage jpgImage = new JpgImage(srcFile,legth,wdith);
                    listJpgImage.add(jpgImage);
                    int prozent = jpgImage.scaleFactor();
                    String command = "magick convert "+srcFile.getAbsolutePath()+" -resize " +prozent+"% -density 96x96 "+srcFile.getAbsolutePath()+"_bak.jpg";
                    log.info(command);
                    //Process process = Runtime.getRuntime().exec(command);
                }
             }
        } catch (ImageReadException e) { } catch (IOException e) { }
        return srcFile;
    }

    public List<JpgImage> getListJpgImage() {
        return listJpgImage;
    }
}
