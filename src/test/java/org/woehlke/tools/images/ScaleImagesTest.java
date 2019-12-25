package org.woehlke.tools.images;

import org.junit.Test;
import org.woehlke.tools.ScaleImages;

public class ScaleImagesTest {

    @Test
    public void runScaleImagesTest(){
        String args[] = {"~/tools"};
        ScaleImages classUnderTest = new ScaleImages(args);
        classUnderTest.run();
    }
}
