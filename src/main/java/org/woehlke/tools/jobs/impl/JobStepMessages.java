package org.woehlke.tools.jobs.impl;

import org.woehlke.tools.db.common.JobCase;

import java.util.Hashtable;
import java.util.Map;

public class JobStepMessages {

    private final Map<String,String> msg;

    public JobStepMessages(String rootDirectory) {
        this.msg = new Hashtable<>();
        for(JobStepSignal jobStepSignal : JobStepSignal.values()){
            for(JobRenameStep step: JobRenameStep.values()){
                String key = getKey(jobStepSignal, step);
                String value = jobStepSignal +" ("+step.getHumanReadable()+"@"+ JobCase.JOB_RENAME_FILES.getHumanReadable() +")  for "+rootDirectory;
                this.msg.put(key,value);
            }
            for(JobScaleImagesStep step: JobScaleImagesStep.values()){
                String key = getKey(jobStepSignal, step);
                String value = jobStepSignal +" ("+step.getHumanReadable()+"@"+ JobCase.JOB_SCALE_IMAGES.getHumanReadable() +")  for "+rootDirectory;
                this.msg.put(key,value);
            }
        }
    }

    public String get(JobStepSignal jobStepSignal, JobScaleImagesStep step){
        String key = getKey(jobStepSignal, step);
        return msg.get(key);
    }

    public String get(JobStepSignal jobStepSignal, JobRenameStep step){
        String key = getKey(jobStepSignal, step);
        return msg.get(key);
    }

    public String getLine(){
        return "***********************************************************";
    }

    public String getKey(JobStepSignal jobStepSignal, JobRenameStep step){
        return jobStepSignal +"_"+step+"_"+JobCase.JOB_RENAME_FILES.name();
    }

    public String getKey(JobStepSignal jobStepSignal, JobScaleImagesStep step){
        return jobStepSignal +"_"+step+"_"+JobCase.JOB_SCALE_IMAGES.name();
    }

}
