package ru.ylab;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Class representing application.
 *
 * @author azatyamanaev
 */
@Log4j2
@EnableAspectJAutoProxy
@EnableWebMvc
@ConfigurationPropertiesScan("ru.ylab.settings")
@SpringBootApplication
public class WebApplication {

    /**
     * Handles main process of an application.
     */
    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }
}
