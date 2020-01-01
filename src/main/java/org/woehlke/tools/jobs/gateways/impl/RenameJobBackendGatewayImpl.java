package org.woehlke.tools.jobs.gateways.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.woehlke.tools.jobs.gateways.RenameJobBackendGateway;
import static org.woehlke.tools.config.properties.PipelineNames.JOB_RENAME_BACKEND_GATEWAY_IMPL;


@Service(JOB_RENAME_BACKEND_GATEWAY_IMPL)
public class RenameJobBackendGatewayImpl implements RenameJobBackendGateway {

    @Autowired
    public RenameJobBackendGatewayImpl() {}

    @Override
    public String listen(String logbuch) {
        return logbuch;
    }

}
