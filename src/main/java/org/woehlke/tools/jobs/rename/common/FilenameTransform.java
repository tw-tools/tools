package org.woehlke.tools.jobs.rename.common;

public class FilenameTransform {

    public static String getNewName(String oldName){
        return oldName
            .replaceAll(" ","_")
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
            .replaceAll("ß","sz");
    }
}
