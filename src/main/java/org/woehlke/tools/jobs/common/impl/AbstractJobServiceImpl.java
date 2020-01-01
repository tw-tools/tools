package org.woehlke.tools.jobs.common.impl;

import org.woehlke.tools.config.properties.ApplicationProperties;
import org.woehlke.tools.jobs.common.AbstractJobService;
import org.woehlke.tools.jobs.traverse.TraverseDirsService;
import org.woehlke.tools.jobs.traverse.TraverseFilesService;
import org.woehlke.tools.model.config.JobEventSignal;
import org.woehlke.tools.model.config.JobEventType;
import org.woehlke.tools.model.entities.Job;
import org.woehlke.tools.model.entities.Logbuch;
import org.woehlke.tools.model.services.JobService;
import org.woehlke.tools.model.services.LogbuchServiceAsync;

import static org.woehlke.tools.model.config.JobEventSignal.*;
import static org.woehlke.tools.model.config.JobEventType.*;

public class AbstractJobServiceImpl extends Thread implements AbstractJobService {

    protected final LogbuchServiceAsync logbuchServiceAsync;
    protected final JobService jobService;
    protected final TraverseDirsService traverseDirsService;
    protected final TraverseFilesService traverseFilesService;
    protected final ApplicationProperties properties;

    protected Job job;

    public AbstractJobServiceImpl(
        LogbuchServiceAsync logbuchServiceAsync,
        JobService jobService,
        TraverseDirsService traverseDirsService, TraverseFilesService traverseFilesService, ApplicationProperties properties
    ) {
        this.logbuchServiceAsync = logbuchServiceAsync;
        this.jobService = jobService;
        this.traverseDirsService = traverseDirsService;
        this.traverseFilesService = traverseFilesService;
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

    protected void signalJobStartToDb(JobEventType jobEventType){
        info(START,jobEventType);
        if(this.properties.getDbActive()) {
            job = jobService.start(job);
        }
    }

    protected void signalJobDoneToDb(JobEventType jobEventType){
        info( DONE, jobEventType);
        if(this.properties.getDbActive()) {
            job = jobService.finish(job);
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
