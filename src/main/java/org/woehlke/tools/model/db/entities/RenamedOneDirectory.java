package org.woehlke.tools.model.db.entities;

import org.woehlke.tools.model.db.config.JobEventType;
import org.woehlke.tools.model.db.config.JobEventSignal;
import org.woehlke.tools.model.db.common.JobEvent;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.io.File;
import java.io.Serializable;
import java.util.Objects;

import static org.woehlke.tools.model.db.common.JobEventDiscriminatorValue.RENAMED_ONE_DIRECTORY;

@Entity
@DiscriminatorValue(RENAMED_ONE_DIRECTORY)
public class RenamedOneDirectory extends JobEvent implements Serializable {

    @Column(length=65000,columnDefinition = "TEXT")
    private String parent;

    @Column(length=65000,columnDefinition = "TEXT")
    private String sourceName;

    @Column(length=65000,columnDefinition = "TEXT")
    private String targetName;

    @Column(length=65000,columnDefinition = "TEXT")
    private String sourcePath;

    @Column(length=65000,columnDefinition = "TEXT")
    private String targetPath;

    public RenamedOneDirectory() {
        super();
    }

    public RenamedOneDirectory(
        File source,
        File target,
        Job myJob,
        JobEventType jobEventType,
        JobEventSignal jobEventSignal
    ) {
        super(myJob, jobEventType, jobEventSignal);
        this.parent = source.getParentFile().getAbsolutePath();
        this.sourceName = source.getName();
        this.targetName = target.getName();
        this.sourcePath = source.getAbsolutePath();
        this.targetPath = target.getAbsolutePath();
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public String getSourcePath() {
        return sourcePath;
    }

    public void setSourcePath(String sourcePath) {
        this.sourcePath = sourcePath;
    }

    public String getTargetPath() {
        return targetPath;
    }

    public void setTargetPath(String targetPath) {
        this.targetPath = targetPath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RenamedOneDirectory)) return false;
        if (!super.equals(o)) return false;
        RenamedOneDirectory that = (RenamedOneDirectory) o;
        return getParent().equals(that.getParent()) &&
            getSourceName().equals(that.getSourceName()) &&
            getTargetName().equals(that.getTargetName()) &&
            getSourcePath().equals(that.getSourcePath()) &&
            getTargetPath().equals(that.getTargetPath());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getParent(), getSourceName(), getTargetName(), getSourcePath(), getTargetPath());
    }

    @Override
    public String toString() {
        return "RenamedOneDirectory{" +
            "parent='" + parent + '\'' +
            ", sourceName='" + sourceName + '\'' +
            ", targetName='" + targetName + '\'' +
            ", sourcePath='" + sourcePath + '\'' +
            ", targetPath='" + targetPath + '\'' +
            "} " + super.toString();
    }
}
