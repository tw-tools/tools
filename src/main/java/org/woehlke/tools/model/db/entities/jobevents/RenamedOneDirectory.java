package org.woehlke.tools.model.db.entities.jobevents;

import org.woehlke.tools.model.db.entities.Job;
import org.woehlke.tools.model.db.entities.JobEvent;
import org.woehlke.tools.config.db.JobEventSignal;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.io.File;
import java.util.Objects;

import static org.woehlke.tools.model.db.entities.parts.JobEventDiscriminatorValue.RENAMED_ONE_DIRECTORY;

@Entity
@DiscriminatorValue(RENAMED_ONE_DIRECTORY)
public class RenamedOneDirectory extends JobEvent {

    @Column(length=65000,columnDefinition = "TEXT")
    private String parent;

    @Column(length=65000,columnDefinition = "TEXT")
    private String source;

    @Column(length=65000,columnDefinition = "TEXT")
    private String target;

    public RenamedOneDirectory() {
        super();
    }

    public RenamedOneDirectory(JobEventSignal jobEventSignal, Job myJob, File source, File target) {
        super(jobEventSignal, myJob);
        this.parent = source.getParentFile().getAbsolutePath();
        this.source = source.getName();
        this.target = target.getName();
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RenamedOneDirectory)) return false;
        if (!super.equals(o)) return false;
        RenamedOneDirectory that = (RenamedOneDirectory) o;
        return getParent().equals(that.getParent()) &&
            getSource().equals(that.getSource()) &&
            getTarget().equals(that.getTarget());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getParent(), getSource(), getTarget());
    }

    @Override
    public String toString() {
        return "JobEventRenamedOneDirectory{" +
            "parent='" + parent + '\'' +
            ", source='" + source + '\'' +
            ", target='" + target + '\'' +
            "} " + super.toString();
    }
}
