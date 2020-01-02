package org.woehlke.tools.jobs.rename.impl;

import java.util.Map;
import java.util.TreeMap;

public class FilenameTransform {

    static Map<String,String> replace = new TreeMap<>();
    static {
        replace.put(" ","-");
        replace.put("Ä","Ae");
        replace.put("Ö","Oe");
        replace.put("Ü","Ue");
        replace.put("ä","ae");
        replace.put("ö","oe");
        replace.put("ü","ue");
        replace.put(",","_");
        replace.put(";","_");
        replace.put("\\(","_");
        replace.put("\\)","_");
        replace.put("ß","sz");
        replace.put("&","-and-");
        replace.put("=","-equals-");
        replace.put("\\?","_");
    }

    public static String getNewName(String oldName){
        String temp;
        for(String key:replace.keySet()){
            temp = oldName.replaceAll(key,replace.get(key));
            oldName = temp;
        }
        return oldName;
    }

    public static boolean toBeRenamed(String oldName){
        for(String key:replace.keySet()){
            if(oldName.indexOf(key) >= 0){
                return true;
            }
        }
        return false;
    }
}
