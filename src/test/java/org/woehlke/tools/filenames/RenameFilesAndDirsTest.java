package org.woehlke.tools.filenames;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.woehlke.tools.db.service.LogbuchQueueService;
import org.woehlke.tools.filesystem.RenameDirectoriesAndFiles;
import org.woehlke.tools.filesystem.TraverseDirs;
import org.woehlke.tools.filesystem.TraverseFiles;
import org.woehlke.tools.filesystem.RenameFilesAndDirs;
import org.woehlke.tools.filesystem.impl.RenameFilesAndDirsImpl;

import java.io.File;

@SpringBootTest
public class RenameFilesAndDirsTest {

    @Autowired
    private LogbuchQueueService logbuchQueueService;

    @Autowired
    private TraverseDirs traverseDirs;

    @Autowired
    private TraverseFiles traverseFiles;

    @Autowired
    private  RenameDirectoriesAndFiles renameDirectoriesAndFiles;

    @Test
    public void runRenameFilesAndDirsTest(){
        String args[] = {"~/tools"};
        File rootDirectory = new File("~/tools");
        boolean dryRun = true;
        RenameFilesAndDirs classUnderTest = new RenameFilesAndDirsImpl(
            logbuchQueueService, traverseDirs,traverseFiles, renameDirectoriesAndFiles,
            logbuchService);
        classUnderTest.setRootDirectory(rootDirectory, dryRun);
        classUnderTest.start();
    }
}
