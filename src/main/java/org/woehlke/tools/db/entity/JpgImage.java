package org.woehlke.tools.db.entity;

import javax.persistence.*;
import java.io.File;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Entity
public class JpgImage implements Serializable,Comparable<JpgImage> {

    private static final long serialVersionUID = 8227369680253280265L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private File jpgFile;

    @Column
    private long length;

    @Column
    private long width;

    @Column
    private UUID uuid;

    public JpgImage() {
        uuid = UUID.randomUUID();
    }

    public JpgImage(File jpgFile, long length, long width) {
        this.jpgFile = jpgFile;
        this.length = length;
        this.width = width;
        uuid = UUID.randomUUID();
    }

    public int scaleFactor(){
        Long hundred = 100L;
        Long x = 1600L;
        Long d;
        if(querFormat()){
            d = Long.divideUnsigned(( hundred * x ), this.width);
        } else {
            d = Long.divideUnsigned(( hundred * x ), this.length);
        }
        return d.intValue();
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JpgImage)) return false;
        JpgImage jpgImage = (JpgImage) o;
        return getLength() == jpgImage.getLength() &&
            getWidth() == jpgImage.getWidth() &&
            Objects.equals(id, jpgImage.id) &&
            getJpgFile().equals(jpgImage.getJpgFile()) &&
            getUuid().equals(jpgImage.getUuid());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, getJpgFile(), getLength(), getWidth(), getUuid());
    }

    @Override
    public int compareTo(JpgImage o) {
        return this.compareTo(o);
    }
}
