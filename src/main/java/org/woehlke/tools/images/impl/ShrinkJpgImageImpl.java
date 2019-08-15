package org.woehlke.tools.images.impl;

import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.common.ImageMetadata;
import org.apache.commons.imaging.formats.jpeg.JpegImageMetadata;
import org.apache.commons.imaging.formats.tiff.TiffField;
import org.apache.commons.imaging.formats.tiff.constants.ExifTagConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.woehlke.tools.images.ShrinkJpgImage;

import java.io.File;
import java.io.IOException;

public class ShrinkJpgImageImpl implements ShrinkJpgImage {

    private static final Logger log = LoggerFactory.getLogger(ShrinkJpgImageImpl.class);

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
                try {
                    final TiffField fieldWidth = jpegMetadata.findEXIFValueWithExactMatch(ExifTagConstants.EXIF_TAG_EXIF_IMAGE_WIDTH);
                    if (fieldWidth != null) {
                        log.info("tagInfo: fieldLength " + fieldWidth.getValueDescription());
                    }
                    final TiffField fieldLength = jpegMetadata.findEXIFValueWithExactMatch(ExifTagConstants.EXIF_TAG_EXIF_IMAGE_LENGTH);
                    if (fieldLength != null) {
                        log.info("tagInfo: fieldLength " + fieldLength.getValueDescription());
                    }
                } catch (NullPointerException e){ }
            }
        } catch (ImageReadException e) { } catch (IOException e) { }
        return srcFile;
    }
}
