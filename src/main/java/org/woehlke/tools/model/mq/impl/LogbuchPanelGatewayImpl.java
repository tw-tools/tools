package org.woehlke.tools.model.mq.impl;

import org.springframework.stereotype.Component;
import org.woehlke.tools.config.db.JobCase;
import org.woehlke.tools.config.mq.LogbuchPanelGateway;

@Component
public class LogbuchPanelGatewayImpl implements LogbuchPanelGateway {


    @Override
    public void sendMessage(String payload, String category, JobCase job) {

    }

    @Override
    public String listen(String logbuch) {
        return logbuch;
    }
}
