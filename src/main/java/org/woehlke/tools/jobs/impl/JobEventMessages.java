package org.woehlke.tools.jobs.impl;

import org.woehlke.tools.db.common.JobCase;

import java.util.Hashtable;
import java.util.Map;

public class JobEventMessages {

    private final Map<String,String> msg;

    public JobEventMessages(String rootDirectory) {
        this.msg = new Hashtable<>();
        for(JobEventSignal jobEventSignal : JobEventSignal.values()){
            for(JobRenameEvent step: JobRenameEvent.values()){
                String key = getKey(jobEventSignal, step);
                String value = jobEventSignal +" "+step.getHumanReadable() +"  for "+rootDirectory;
                this.msg.put(key,value);
            }
            for(JobScaleImagesEvent step: JobScaleImagesEvent.values()){
                String key = getKey(jobEventSignal, step);
                String value = jobEventSignal +" "+step.getHumanReadable() +"  for "+rootDirectory;
                this.msg.put(key,value);
            }
            for(JobCase jobCase:JobCase.values()){
                String key = getKey(jobEventSignal, jobCase);
                String value = jobEventSignal +" "+jobCase.getHumanReadable() +"  for "+rootDirectory;
                this.msg.put(key,value);
            }
        }
    }

    public String get(JobEventSignal jobEventSignal, JobScaleImagesEvent step){
        String key = getKey(jobEventSignal, step);
        return msg.get(key);
    }

    public String get(JobEventSignal jobEventSignal, JobRenameEvent step){
        String key = getKey(jobEventSignal, step);
        return msg.get(key);
    }

    public String get(JobEventSignal jobEventSignal, JobCase jobCase){
        String key = getKey(jobEventSignal, jobCase);
        return msg.get(key);
    }

    public String getLine(){
        return "***********************************************************";
    }

    public String getKey(JobEventSignal jobEventSignal, JobRenameEvent step){
        return jobEventSignal +"_"+step+"_"+JobCase.JOB_RENAME_FILES.name();
    }

    public String getKey(JobEventSignal jobEventSignal, JobScaleImagesEvent step){
        return jobEventSignal +"_"+step+"_"+JobCase.JOB_SCALE_IMAGES.name();
    }

    public String getKey(JobEventSignal jobEventSignal, JobCase jobCase){
        return jobEventSignal +"__"+jobCase.name();
    }
}
