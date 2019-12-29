package org.woehlke.tools;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.Test;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;
import org.woehlke.tools.filenames.JobRenameTest;

import java.awt.*;

@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.NONE)
class ToolsApplicationTests {

	@Test
	void contextLoadsTest() {
        log.warn("START contextLoadsTest");
        String[] args = {};
        log.warn("configure Spring ApplicationContext");
        ConfigurableApplicationContext ctx = new SpringApplicationBuilder(ToolsApplication.class)
            .web(WebApplicationType.NONE)
            .headless(false)
            .run(args);
        log.warn("EventQueue.invokeLater");
        EventQueue.invokeLater(() -> {
            log.warn("EventQueue.invokeLater: ctx.getBean");
            ToolsApplication toolsApplication = ctx.getBean(ToolsApplication.class);
            //log.warn("EventQueue.invokeLater:   toolsApplication.start()");
            //toolsApplication.start();
            log.warn("EventQueue.invokeLater:   toolsApplication.exit()");
            toolsApplication.exit();
        });
        log.warn("DONE contextLoadsTest");
	}

    @Test
    void startToolsApplicationTest() {
        log.warn("START");
        String[] args = {};
        log.warn("configure Spring ApplicationContext");
        ConfigurableApplicationContext ctx = new SpringApplicationBuilder(ToolsApplication.class)
            .web(WebApplicationType.NONE)
            .headless(false)
            .run(args);
        log.warn("EventQueue.invokeLater");
        EventQueue.invokeLater(() -> {
            log.warn("EventQueue.invokeLater: ctx.getBean");
            ToolsApplication toolsApplication = ctx.getBean(ToolsApplication.class);
            log.warn("EventQueue.invokeLater:   toolsApplication.start()");
            toolsApplication.start();
            log.warn("EventQueue.invokeLater:   toolsApplication.exit()");
            toolsApplication.exit();
        });
        log.warn("DONE");
    }

    private final boolean dryRun = true;

    private Log log = LogFactory.getLog(JobRenameTest.class);

}
