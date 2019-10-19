package org.woehlke.tools.images;

import org.junit.Test;

public class ScaleImagesTest {

    @Test
    public void runScaleImagesTest(){
        String args[] = {"~/tools"};
        ScaleImages classUnderTest = new ScaleImages(args);
        classUnderTest.run();
    }
}
