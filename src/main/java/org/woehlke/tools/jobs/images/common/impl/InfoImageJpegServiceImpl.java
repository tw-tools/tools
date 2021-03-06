package org.woehlke.tools.jobs.images.common.impl;

import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.common.ImageMetadata;
import org.apache.commons.imaging.common.RationalNumber;
import org.apache.commons.imaging.formats.jpeg.JpegImageMetadata;
import org.apache.commons.imaging.formats.tiff.TiffField;
import org.apache.commons.imaging.formats.tiff.TiffImageMetadata;
import org.apache.commons.imaging.formats.tiff.constants.ExifTagConstants;
import org.apache.commons.imaging.formats.tiff.constants.GpsTagConstants;
import org.apache.commons.imaging.formats.tiff.constants.TiffTagConstants;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.woehlke.tools.jobs.images.common.InfoImageJpegService;


import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InfoImageJpegServiceImpl implements InfoImageJpegService {

    @Autowired
    public InfoImageJpegServiceImpl() { }

    @Override
    public String analyseFileContentInformation(String filepath) {
        logger.info("IMAGE_JPEG: "+filepath);
        File fileObj = new File(filepath);
        final ImageMetadata metadata;
        try {
            metadata = Imaging.getMetadata(fileObj);
            logger.info(metadata.toString());
            if (metadata instanceof JpegImageMetadata) {
                final JpegImageMetadata jpegMetadata = (JpegImageMetadata) metadata;
                // Jpeg EXIF metadata is stored in a TIFF-based directory structure
                // and is identified with TIFF tags.
                // Here we look for the "x resolution" tag, but
                // we could just as easily search for any other tag.
                //
                // see the TiffConstants file for a list of TIFF tags.
                logger.info("file: " + filepath);
                // print out various interesting EXIF tags.
                List<TagInfo> allTagInfos = ExifTagConstants.ALL_EXIF_TAGS;
                for(TagInfo tagInfo:allTagInfos){
                    final TiffField field = jpegMetadata.findEXIFValueWithExactMatch(tagInfo);
                    if (field == null) {
                        logger.info("tagInfo: "+tagInfo.name + ": " + "Not Found.");
                    } else {
                        logger.info("tagInfo: "+tagInfo.name + ": " + field.getValueDescription());
                    }
                }
                printTagValue(jpegMetadata, TiffTagConstants.TIFF_TAG_XRESOLUTION);
                printTagValue(jpegMetadata, TiffTagConstants.TIFF_TAG_DATE_TIME);
                printTagValue(jpegMetadata,
                        ExifTagConstants.EXIF_TAG_DATE_TIME_ORIGINAL);
                printTagValue(jpegMetadata, ExifTagConstants.EXIF_TAG_DATE_TIME_DIGITIZED);
                printTagValue(jpegMetadata, ExifTagConstants.EXIF_TAG_ISO);
                printTagValue(jpegMetadata,
                        ExifTagConstants.EXIF_TAG_SHUTTER_SPEED_VALUE);
                printTagValue(jpegMetadata,
                        ExifTagConstants.EXIF_TAG_APERTURE_VALUE);
                printTagValue(jpegMetadata,
                        ExifTagConstants.EXIF_TAG_BRIGHTNESS_VALUE);
                printTagValue(jpegMetadata,
                        GpsTagConstants.GPS_TAG_GPS_LATITUDE_REF);
                printTagValue(jpegMetadata, GpsTagConstants.GPS_TAG_GPS_LATITUDE);
                printTagValue(jpegMetadata,
                        GpsTagConstants.GPS_TAG_GPS_LONGITUDE_REF);
                printTagValue(jpegMetadata, GpsTagConstants.GPS_TAG_GPS_LONGITUDE);
                logger.info("");
                // simple interface to GPS data
                final TiffImageMetadata exifMetadata = jpegMetadata.getExif();
                if (null != exifMetadata) {
                    final TiffImageMetadata.GPSInfo gpsInfo = exifMetadata.getGPS();
                    if (null != gpsInfo) {
                        final String gpsDescription = gpsInfo.toString();
                        final double longitude = gpsInfo.getLongitudeAsDegreesEast();
                        final double latitude = gpsInfo.getLatitudeAsDegreesNorth();
                        logger.info("    " + "GPS Description: "
                                + gpsDescription);
                        logger.info("    "
                                + "GPS Longitude (Degrees East): " + longitude);
                        logger.info("    "
                                + "GPS Latitude (Degrees North): " + latitude);
                    }
                }
                // more specific example of how to manually access GPS values
                final TiffField gpsLatitudeRefField = jpegMetadata.findEXIFValueWithExactMatch(
                        GpsTagConstants.GPS_TAG_GPS_LATITUDE_REF);
                final TiffField gpsLatitudeField = jpegMetadata.findEXIFValueWithExactMatch(
                        GpsTagConstants.GPS_TAG_GPS_LATITUDE);
                final TiffField gpsLongitudeRefField = jpegMetadata.findEXIFValueWithExactMatch(
                        GpsTagConstants.GPS_TAG_GPS_LONGITUDE_REF);
                final TiffField gpsLongitudeField = jpegMetadata.findEXIFValueWithExactMatch(
                        GpsTagConstants.GPS_TAG_GPS_LONGITUDE);
                if (gpsLatitudeRefField != null && gpsLatitudeField != null &&
                        gpsLongitudeRefField != null &&
                        gpsLongitudeField != null) {
                    // all of these values are strings.
                    final String gpsLatitudeRef = (String) gpsLatitudeRefField.getValue();
                    final RationalNumber gpsLatitude[] = (RationalNumber[]) (gpsLatitudeField.getValue());
                    final String gpsLongitudeRef = (String) gpsLongitudeRefField.getValue();
                    final RationalNumber gpsLongitude[] = (RationalNumber[]) gpsLongitudeField.getValue();
                    //
                    final RationalNumber gpsLatitudeDegrees = gpsLatitude[0];
                    final RationalNumber gpsLatitudeMinutes = gpsLatitude[1];
                    final RationalNumber gpsLatitudeSeconds = gpsLatitude[2];
                    //
                    final RationalNumber gpsLongitudeDegrees = gpsLongitude[0];
                    final RationalNumber gpsLongitudeMinutes = gpsLongitude[1];
                    final RationalNumber gpsLongitudeSeconds = gpsLongitude[2];
                    // This will format the gps info like so:
                    //
                    // gpsLatitude: 8 degrees, 40 minutes, 42.2 seconds S
                    // gpsLongitude: 115 degrees, 26 minutes, 21.8 seconds E
                    logger.info("    " + "GPS Latitude: "
                            + gpsLatitudeDegrees.toDisplayString() + " degrees, "
                            + gpsLatitudeMinutes.toDisplayString() + " minutes, "
                            + gpsLatitudeSeconds.toDisplayString() + " seconds "
                            + gpsLatitudeRef);
                    logger.info("    " + "GPS Longitude: "
                            + gpsLongitudeDegrees.toDisplayString() + " degrees, "
                            + gpsLongitudeMinutes.toDisplayString() + " minutes, "
                            + gpsLongitudeSeconds.toDisplayString() + " seconds "
                            + gpsLongitudeRef);
                }
                logger.info("");
                final List<ImageMetadata.ImageMetadataItem> items = jpegMetadata.getItems();
                for (int i = 0; i < items.size(); i++) {
                    final ImageMetadata.ImageMetadataItem item = items.get(i);
                    logger.info("    " + "item: " + item);
                }
                logger.info("");
            }
        } catch (ImageReadException | IOException e) {
            logger.warn(e.getMessage());
        }
        return "";
    }

    @Override
    public Map<String,String> getFileInfo(String filepath) {
        Map<String,String> info = new HashMap<String,String>();
        logger.info("IMAGE_JPEG: "+filepath);
        File fileObj = new File(filepath);
        final ImageMetadata metadata;
        try {
            metadata = Imaging.getMetadata(fileObj);
            logger.info(metadata.toString());
            if (metadata instanceof JpegImageMetadata) {
                final JpegImageMetadata jpegMetadata = (JpegImageMetadata) metadata;
                List<TagInfo> allTagInfos = ExifTagConstants.ALL_EXIF_TAGS;
                for(TagInfo tagInfo:allTagInfos){
                    try {
                        final TiffField field = jpegMetadata.findEXIFValueWithExactMatch(tagInfo);
                        if (field == null) {
                            logger.info("tagInfo: " + tagInfo.name + ": " + "Not Found.");
                        } else {
                            logger.info("tagInfo: " + tagInfo.name + ": " + field.getValueDescription());
                            info.put(tagInfo.name, field.getValueDescription());
                        }
                    } catch (NullPointerException e){
                        logger.info("tagInfo: " + e.getMessage());
                        e.printStackTrace();
                    }
                }
            }
        } catch (ImageReadException | IOException e) {
            logger.warn(e.getMessage());
        }
        return info;
    }

    private void printTagValue(final JpegImageMetadata jpegMetadata,
                               final TagInfo tagInfo) {
        final TiffField field = jpegMetadata.findEXIFValueWithExactMatch(tagInfo);
        if (field == null) {
            logger.info(tagInfo.name + ": " + "Not Found.");
        } else {
            logger.info(tagInfo.name + ": "
                    + field.getValueDescription());
        }
    }

    @Override
    public JpegImageMetadata getImageMetadata(File srcFileCopy){
        JpegImageMetadata jpegMetadata = null;
        ImageMetadata metadata = null;
        try {
            metadata = Imaging.getMetadata(srcFileCopy);
        } catch (NullPointerException | ImageReadException | IOException e) {
            logger.warn(e.getMessage());
        }
        if ((metadata != null) && (metadata instanceof JpegImageMetadata)) {
            jpegMetadata = (JpegImageMetadata) metadata;
        }
        return jpegMetadata;
    }

    @Override
    public long getWidth(final JpegImageMetadata jpegMetadata){
        long width = 0L;
        try {
            final TiffField fieldWidth = jpegMetadata.findEXIFValueWithExactMatch(
                TiffTagConstants.TIFF_TAG_IMAGE_WIDTH
            );
            if (fieldWidth != null) {
                width = fieldWidth.getIntValue();
            }
        } catch (NullPointerException | ImageReadException e) {
            logger.warn(e.getMessage());
        }
        return width;
    }

    @Override
    public long getLength(final JpegImageMetadata jpegMetadata){
        long length = 0L;
        try {
            final TiffField fieldLength = jpegMetadata.findEXIFValueWithExactMatch(
                TiffTagConstants.TIFF_TAG_IMAGE_LENGTH
            );
            if (fieldLength != null) {
                length = fieldLength.getIntValue();
            }
        } catch (NullPointerException | ImageReadException e) {
            logger.warn(e.getMessage());
        }
        return length;
    }

    private Log logger = LogFactory.getLog(InfoImageJpegServiceImpl.class);
}
