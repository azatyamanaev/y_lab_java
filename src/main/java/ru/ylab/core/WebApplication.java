package ru.ylab.core;

import lombok.extern.log4j.Log4j2;
import org.springdoc.core.configuration.SpringDocConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import ru.ylab.swaggerstarter.annotations.EnableSwaggerStarter;

/**
 * Class representing application.
 *
 * @author azatyamanaev
 */
@Log4j2
@EnableAspectJAutoProxy
@EnableWebMvc
@EnableSwaggerStarter
@ConfigurationPropertiesScan("ru.ylab.core.settings")
@SpringBootApplication(exclude = {SpringDocConfiguration.class})
public class WebApplication {

    /**
     * Handles main process of an application.
     */
    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }
}
