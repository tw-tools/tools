package org.woehlke.tools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.integration.config.EnableIntegration;
import org.woehlke.tools.config.application.DataSourceConfig;
import org.woehlke.tools.config.application.PipelineConfig;
import org.woehlke.tools.config.application.QueueConfig;
import org.woehlke.tools.config.properties.ApplicationProperties;
import org.woehlke.tools.config.properties.MmiProperties;

import java.awt.*;


@SpringBootApplication
@Configuration
@EnableIntegration
@Import({
    DataSourceConfig.class,
    QueueConfig.class,
    PipelineConfig.class,
    ApplicationProperties.class,
    MmiProperties.class
})
public class ToolsApplication {

    private final ToolsApplicationFrame toolsApplicationFrame;

    @Autowired
    public ToolsApplication(ToolsApplicationFrame toolsApplicationFrame) {
        this.toolsApplicationFrame = toolsApplicationFrame;
    }

    public void start(){
        this.toolsApplicationFrame.showme();
    }

    public void exit() {
        this.toolsApplicationFrame.dispose();
    }

	public static void main(String[] args) {
        ConfigurableApplicationContext ctx = new SpringApplicationBuilder(ToolsApplication.class)
            .web(WebApplicationType.NONE)
            .headless(false)
            .run(args);
        EventQueue.invokeLater(() -> {
            ToolsApplication ex = ctx.getBean(ToolsApplication.class);
            ex.start();
        });
	}

}
