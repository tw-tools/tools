package org.woehlke.tools.jobs.images.impl;

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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.woehlke.tools.jobs.common.LogbuchQueueService;
import org.woehlke.tools.jobs.images.InfoImageJpeg;
import org.woehlke.tools.jobs.mq.impl.JobScaleImagesQueueImpl;


import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class InfoImageJpegImpl implements InfoImageJpeg {

    private final LogbuchQueueService log;

    @Autowired
    public InfoImageJpegImpl(@Qualifier("jobScaleImagesQueueImpl") LogbuchQueueService log) {
        this.log = log;
    }

    @Override
    public String analyseFileContentInformation(String filepath) {
        log.info("IMAGE_JPEG: "+filepath);
        File fileObj = new File(filepath);
        final ImageMetadata metadata;
        try {
            metadata = Imaging.getMetadata(fileObj);
            log.info(metadata.toString());
            if (metadata instanceof JpegImageMetadata) {
                final JpegImageMetadata jpegMetadata = (JpegImageMetadata) metadata;
                // Jpeg EXIF metadata is stored in a TIFF-based directory structure
                // and is identified with TIFF tags.
                // Here we look for the "x resolution" tag, but
                // we could just as easily search for any other tag.
                //
                // see the TiffConstants file for a list of TIFF tags.
                log.info("file: " + filepath);
                // print out various interesting EXIF tags.
                List<TagInfo> allTagInfos = ExifTagConstants.ALL_EXIF_TAGS;
                for(TagInfo tagInfo:allTagInfos){
                    final TiffField field = jpegMetadata.findEXIFValueWithExactMatch(tagInfo);
                    if (field == null) {
                        log.info("tagInfo: "+tagInfo.name + ": " + "Not Found.");
                    } else {
                        log.info("tagInfo: "+tagInfo.name + ": " + field.getValueDescription());
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
                log.info("");
                // simple interface to GPS data
                final TiffImageMetadata exifMetadata = jpegMetadata.getExif();
                if (null != exifMetadata) {
                    final TiffImageMetadata.GPSInfo gpsInfo = exifMetadata.getGPS();
                    if (null != gpsInfo) {
                        final String gpsDescription = gpsInfo.toString();
                        final double longitude = gpsInfo.getLongitudeAsDegreesEast();
                        final double latitude = gpsInfo.getLatitudeAsDegreesNorth();
                        log.info("    " + "GPS Description: "
                                + gpsDescription);
                        log.info("    "
                                + "GPS Longitude (Degrees East): " + longitude);
                        log.info("    "
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
                    log.info("    " + "GPS Latitude: "
                            + gpsLatitudeDegrees.toDisplayString() + " degrees, "
                            + gpsLatitudeMinutes.toDisplayString() + " minutes, "
                            + gpsLatitudeSeconds.toDisplayString() + " seconds "
                            + gpsLatitudeRef);
                    log.info("    " + "GPS Longitude: "
                            + gpsLongitudeDegrees.toDisplayString() + " degrees, "
                            + gpsLongitudeMinutes.toDisplayString() + " minutes, "
                            + gpsLongitudeSeconds.toDisplayString() + " seconds "
                            + gpsLongitudeRef);
                }
                log.info("");
                final List<ImageMetadata.ImageMetadataItem> items = jpegMetadata.getItems();
                for (int i = 0; i < items.size(); i++) {
                    final ImageMetadata.ImageMetadataItem item = items.get(i);
                    log.info("    " + "item: " + item);
                }
                log.info("");
            }
        } catch (ImageReadException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public Map<String,String> getFileInfo(String filepath) {
        Map<String,String> info = new HashMap<String,String>();
        log.info("IMAGE_JPEG: "+filepath);
        File fileObj = new File(filepath);
        final ImageMetadata metadata;
        try {
            metadata = Imaging.getMetadata(fileObj);
            log.info(metadata.toString());
            if (metadata instanceof JpegImageMetadata) {
                final JpegImageMetadata jpegMetadata = (JpegImageMetadata) metadata;
                List<TagInfo> allTagInfos = ExifTagConstants.ALL_EXIF_TAGS;
                for(TagInfo tagInfo:allTagInfos){
                    try {
                        final TiffField field = jpegMetadata.findEXIFValueWithExactMatch(tagInfo);
                        if (field == null) {
                            log.info("tagInfo: " + tagInfo.name + ": " + "Not Found.");
                        } else {
                            log.info("tagInfo: " + tagInfo.name + ": " + field.getValueDescription());
                            info.put(tagInfo.name, field.getValueDescription());
                        }
                    } catch (NullPointerException e){
                        log.info("tagInfo: " + e.getMessage());
                        e.printStackTrace();
                    }
                }
            }
        } catch (ImageReadException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return info;
    }

    private void printTagValue(final JpegImageMetadata jpegMetadata,
                               final TagInfo tagInfo) {
        final TiffField field = jpegMetadata.findEXIFValueWithExactMatch(tagInfo);
        if (field == null) {
            log.info(tagInfo.name + ": " + "Not Found.");
        } else {
            log.info(tagInfo.name + ": "
                    + field.getValueDescription());
        }
    }

    private Log logger = LogFactory.getLog(InfoImageJpegImpl.class);
}
