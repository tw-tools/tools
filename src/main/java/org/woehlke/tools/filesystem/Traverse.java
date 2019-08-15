package org.woehlke.tools.filesystem;

import java.io.File;
import java.util.Deque;

public interface Traverse extends Runnable {
    String getDataRootDir();
    Deque<File> getResult();
}
