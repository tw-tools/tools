package org.woehlke.tools.beans;


import java.io.File;


public class RenameFilesAndDirsFileRequester {

    private File rootDir;

    public RenameFilesAndDirsFileRequester() {
    }

    public File getRootDir() {
        return rootDir;
    }

    public void setRootDir(File rootDir) {
        this.rootDir = rootDir;
    }
}
