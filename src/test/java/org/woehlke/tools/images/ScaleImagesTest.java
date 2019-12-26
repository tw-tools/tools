package org.woehlke.tools.images;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.woehlke.tools.db.service.DbLogger;
import org.woehlke.tools.filesystem.TraverseDirs;
import org.woehlke.tools.filesystem.TraverseFiles;
import org.woehlke.tools.images.impl.ScaleImagesImpl;

@SpringBootTest
public class ScaleImagesTest {

    @Autowired
    private DbLogger dbLogger;

    @Autowired
    private TraverseDirs traverseDirs;

    @Autowired
    private TraverseFiles traverseFiles;

    @Test
    public void runScaleImagesTest(){
        String args[] = {"~/tools"};
        ScaleImagesImpl classUnderTest = new ScaleImagesImpl(dbLogger, traverseDirs, traverseFiles, shrinkImages);
        classUnderTest.run();
    }
}
