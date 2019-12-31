package org.woehlke.tools.config.db;

public class FilenameTransform {

    public static String getNewName(String oldName){
        return oldName
            .replaceAll(" ","-")
            .replaceAll("Ä","Ae")
            .replaceAll("Ö","Oe")
            .replaceAll("Ü","Ue")
            .replaceAll("ä","ae")
            .replaceAll("ö","oe")
            .replaceAll("ü","ue")
            .replaceAll(",","_")
            .replaceAll(";","_")
            .replaceAll("\\(","_")
            .replaceAll("\\)","_")
            .replaceAll("ß","sz")
            .replaceAll("&","-and-")
            .replaceAll("=","-equals-")
            .replaceAll("\\?","_");
    }
}
