package org.woehlke.tools;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.integration.config.EnableIntegration;
import org.woehlke.tools.view.ToolsApplicationFrame;

import java.awt.*;


@SpringBootApplication
public class ToolsApplication {

    private final ToolsApplicationFrame toolsApplicationFrame;

    @Autowired
    public ToolsApplication(ToolsApplicationFrame toolsApplicationFrame) {
        this.toolsApplicationFrame = toolsApplicationFrame;
    }

    public void start(){
        this.toolsApplicationFrame.showme();
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
