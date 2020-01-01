package org.woehlke.tools.config.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.woehlke.tools.config.properties.ApplicationProperties;
import org.woehlke.tools.jobs.rename.JobRenameBackendGateway;
import org.woehlke.tools.jobs.rename.JobRenamePanelGateway;

import static org.woehlke.tools.config.properties.QueueNames.*;

@Configuration
@EnableIntegration
@Import(QueueConfig.class)
public class RenameFilesConfig {

    @Autowired
    @Qualifier(LOGBUCH_QUEUE)
    QueueChannel logbuchChannel;

    @Autowired
    @Qualifier(JOB_RENAME_FILES_QUEUE)
    QueueChannel renameFilesChannel;

    @Autowired
    @Qualifier("jobRenameFilesBackendGatewayImpl")
    JobRenameBackendGateway jobRenameBackendGateway;

    @Autowired
    @Qualifier("jobRenameFilesPanel")
    JobRenamePanelGateway jobRenamePanelGateway;

    @Bean
    public IntegrationFlow renameFilesPipeline() {
        return IntegrationFlows
            .from(renameFilesChannel)
            .handle(jobRenamePanelGateway,"listen")  //.log()
            .handle(jobRenameBackendGateway,"listen")  //.log()
            .channel(logbuchChannel).get();
    }



}
