package org.woehlke.tools.model.jobs.common;

import org.springframework.stereotype.Component;
import org.woehlke.tools.config.db.JobCase;
import org.woehlke.tools.config.db.JobEventSignal;
import org.woehlke.tools.config.db.JobRenameEvent;
import org.woehlke.tools.config.db.JobScaleImagesEvent;

import java.io.File;
import java.util.Hashtable;
import java.util.Map;

@Component
public class JobEventMessages {

    private final Map<String,String> msg;

    public JobEventMessages() {
        this.msg = new Hashtable<>();
    }

    public void setRooTDirectory(File rooTDirectoryFile){
        String rootDirectory = rooTDirectoryFile.getAbsolutePath();
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
