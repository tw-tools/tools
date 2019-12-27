package org.woehlke.tools.jobs.mq.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.ErrorHandler;

import java.util.ArrayList;
import java.util.List;

public class ErrorHandlerLog implements ErrorHandler {

    private Log log = LogFactory.getLog(ErrorHandlerLog.class);

    private final String prefix = "MQ Error: ";
    private final String line = "--------------------------------------------------";

    @Override
    public void handleError(Throwable t) {
        List<String> stringList = new ArrayList<>();
        stringList.add(line);
        stringList.add(t.getMessage());
        stringList.add("init cause: "+ t.getCause());
        for(StackTraceElement element: t.getStackTrace()){
           String msg = "stacktace: "
                + element.getClassName() + " -> "
                + element.getMethodName() + " in: "
                + element.getFileName() + " line: "
                + element.getLineNumber() + "";
            stringList.add(msg);
        }
        for (String logLine:stringList){
            log.warn(prefix+logLine);
        }
    }
}
