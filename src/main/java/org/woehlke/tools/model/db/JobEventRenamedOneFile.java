package org.woehlke.tools.model.db;

import org.woehlke.tools.model.db.config.JobEventSignal;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.io.File;

@Entity
@DiscriminatorValue("RenamedOneFile")
public class JobEventRenamedOneFile extends JobEventRenamedOneDirectory {

    public JobEventRenamedOneFile() {
        super();
    }

    public JobEventRenamedOneFile(
        JobEventSignal jobEventSignal,
        Job myJob,
        File source,
        File target
    ) {
        super(jobEventSignal, myJob, source, target);
    }

    @Override
    public String toString() {
        return "JobEventRenamedOneFile{} " + super.toString();
    }
}
