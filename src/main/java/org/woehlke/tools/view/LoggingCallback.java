package org.woehlke.tools.view;

public interface LoggingCallback {

    void info(String msg);

    void info(String msg, String category, String job);

    void info(String msg, String category);
}
