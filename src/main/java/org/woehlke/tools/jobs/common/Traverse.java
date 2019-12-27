package org.woehlke.tools.jobs.common;

import java.io.File;
import java.util.Deque;

public interface Traverse extends Runnable {

    void add(final String dataRootDir);



    Deque<File> getResult();
}
