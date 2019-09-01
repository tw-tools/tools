package org.woehlke.tools.filenames;

import org.junit.Test;

public class RenameFilesAndDirsTest {

    @Test
    public void runRenameFilesAndDirsTest(){
        String args[] = {"C:/www"};
        RenameFilesAndDirs classUnderTest = new RenameFilesAndDirs(args);
        classUnderTest.run();
    }
}
