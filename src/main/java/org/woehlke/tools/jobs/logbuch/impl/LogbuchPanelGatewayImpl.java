package org.woehlke.tools.jobs.logbuch.impl;

import org.springframework.stereotype.Component;
import org.woehlke.tools.jobs.config.JobCase;
import org.woehlke.tools.view.mq.LogbuchPanelGateway;

@Component("logbuchPanelGatewayImpl")
public class LogbuchPanelGatewayImpl implements LogbuchPanelGateway {


    @Override
    public void sendMessage(String payload, String category, JobCase job) {

    }

    @Override
    public String listen(String logbuch) {
        return logbuch;
    }
}
