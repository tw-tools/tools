package org.woehlke.tools.filenames;

import org.junit.jupiter.api.Test;
import org.woehlke.tools.view.RenameFilesAndDirs;

public class RenameFilesAndDirsTest {

    @Test
    public void runRenameFilesAndDirsTest(){
        String args[] = {"~/tools"};
        RenameFilesAndDirs classUnderTest = new RenameFilesAndDirs(args);
        classUnderTest.run();
    }
}
