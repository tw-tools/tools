package org.woehlke.tools.jobs.common.impl;

import org.woehlke.tools.config.properties.ApplicationProperties;
import org.woehlke.tools.jobs.common.AbstractJobService;
import org.woehlke.tools.model.config.JobEventSignal;
import org.woehlke.tools.model.config.JobEventType;
import org.woehlke.tools.model.entities.Job;
import org.woehlke.tools.model.entities.Logbuch;
import org.woehlke.tools.model.services.LogbuchServiceAsync;

import static org.woehlke.tools.model.config.JobEventSignal.INFO;
import static org.woehlke.tools.model.config.JobEventType.FYI;

public class AbstractJobServiceImpl extends Thread implements AbstractJobService {

    protected final LogbuchServiceAsync logbuchServiceAsync;
    protected final ApplicationProperties properties;

    protected Job job;

    public AbstractJobServiceImpl(LogbuchServiceAsync logbuchServiceAsync, ApplicationProperties properties) {
        this.logbuchServiceAsync = logbuchServiceAsync;
        this.properties = properties;
    }

    protected void info(String line, JobEventSignal jobEventSignal, JobEventType jobEventType){
        if(this.properties.getDbActive()) {
            String category = job.getJobCase().getHumanReadable() + job.getRootDirectory();
            Logbuch jobEvent = new Logbuch(
                line,
                category,
                job,
                jobEventType,
                jobEventSignal
            );
            this.logbuchServiceAsync.add(jobEvent);
        }
    }

    protected void info( String line ){
        JobEventSignal jobEventSignal = INFO;
        JobEventType jobEventType = FYI;
        info(line, jobEventSignal,jobEventType);
    }

    protected void info(JobEventSignal jobEventSignal, JobEventType step){
        String line = jobEventSignal.name() + " " + step.getHumanReadable() + " " + step.getJobCase().getHumanReadable();
        JobEventType jobEventType = JobEventType.LOGUBUCH_EVENT;
        info( line, jobEventSignal, jobEventType);
    }

    protected void line(){
        info("###############################################");
    }
}
