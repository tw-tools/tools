package org.woehlke.tools.images.model;

import java.io.File;
import java.io.Serializable;
import java.util.Objects;

public class JpgImage implements Serializable,Comparable<JpgImage> {

    private final File jpgFile;
    private final long length;
    private final long width;

    public JpgImage(File jpgFile, long length, long width) {
        this.jpgFile = jpgFile;
        this.length = length;
        this.width = width;
    }

    public int scaleFactor(){
        Long hundred = 100L;
        Long x = 1048L;
        Long d;
        if(querFormat()){
            d = ( hundred * x ) / this.width;
        } else {
            d = ( hundred * x ) / this.length;
        }
        return d.intValue();
    }

    public boolean querFormat(){
        return this.width > this.length;
    }

    public File getJpgFile() {
        return jpgFile;
    }

    public long getLength() {
        return length;
    }

    public long getWidth() {
        return width;
    }

    @Override
    public int compareTo(JpgImage o) {
        return this.jpgFile.getAbsolutePath().compareTo(o.jpgFile.getAbsolutePath());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JpgImage)) return false;
        JpgImage jpgImage = (JpgImage) o;
        return getLength() == jpgImage.getLength() &&
            getWidth() == jpgImage.getWidth() &&
            getJpgFile().equals(jpgImage.getJpgFile());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getJpgFile(), getLength(), getWidth());
    }
}
