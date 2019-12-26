package org.woehlke.tools;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.Test;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;
import org.woehlke.tools.filenames.RenameFilesAndDirsTest;

import java.awt.*;

@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.NONE)
class ToolsApplicationTests {

	@Test
	void contextLoadsTest() {
        log.info("START contextLoadsTest");
        String[] args = {};
        log.info("configure Spring ApplicationContext");
        ConfigurableApplicationContext ctx = new SpringApplicationBuilder(ToolsApplication.class)
            .web(WebApplicationType.NONE)
            .headless(false)
            .run(args);
        log.info("EventQueue.invokeLater");
        EventQueue.invokeLater(() -> {
            log.info("EventQueue.invokeLater: ctx.getBean");
            ToolsApplication toolsApplication = ctx.getBean(ToolsApplication.class);
            //log.info("EventQueue.invokeLater:   toolsApplication.start()");
            //toolsApplication.start();
            log.info("EventQueue.invokeLater:   toolsApplication.exit()");
            toolsApplication.exit();
        });
        log.info("DONE contextLoadsTest");
	}

    @Test
    void startToolsApplicationTest() {
        log.info("START");
        String[] args = {};
        log.info("configure Spring ApplicationContext");
        ConfigurableApplicationContext ctx = new SpringApplicationBuilder(ToolsApplication.class)
            .web(WebApplicationType.NONE)
            .headless(false)
            .run(args);
        log.info("EventQueue.invokeLater");
        EventQueue.invokeLater(() -> {
            log.info("EventQueue.invokeLater: ctx.getBean");
            ToolsApplication toolsApplication = ctx.getBean(ToolsApplication.class);
            log.info("EventQueue.invokeLater:   toolsApplication.start()");
            toolsApplication.start();
            log.info("EventQueue.invokeLater:   toolsApplication.exit()");
            toolsApplication.exit();
        });
        log.info("DONE");
    }

    private final boolean dryRun = true;

    private Log log = LogFactory.getLog(RenameFilesAndDirsTest.class);

}
