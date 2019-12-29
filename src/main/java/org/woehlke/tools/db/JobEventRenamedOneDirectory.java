package org.woehlke.tools.db;

import org.woehlke.tools.jobs.impl.JobEventSignal;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.io.File;
import java.util.Objects;

@Entity
@DiscriminatorValue("RenamedOneDirectory")
public class JobEventRenamedOneDirectory  extends JobEvent {

    @Column(length=4096)
    private String parent;

    @Column(length=4096)
    private String source;

    @Column(length=4096)
    private String target;

    public JobEventRenamedOneDirectory() {
        super();
    }

    public JobEventRenamedOneDirectory(JobEventSignal jobEventSignal, Job myJob, File source, File target) {
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
        if (!(o instanceof JobEventRenamedOneDirectory)) return false;
        if (!super.equals(o)) return false;
        JobEventRenamedOneDirectory that = (JobEventRenamedOneDirectory) o;
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
