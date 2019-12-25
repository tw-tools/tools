package org.woehlke.tools.filenames;

import org.junit.Test;
import org.woehlke.tools.RenameFilesAndDirs;

public class RenameFilesAndDirsTest {

    @Test
    public void runRenameFilesAndDirsTest(){
        String args[] = {"~/tools"};
        RenameFilesAndDirs classUnderTest = new RenameFilesAndDirs(args);
        classUnderTest.run();
    }
}
