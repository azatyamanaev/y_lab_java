package ru.spring.swaggerstarter.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;
import ru.spring.swaggerstarter.config.SwaggerConfiguration;

/**
 * Annotation for enabling swagger-starter.
 *
 * @author azatyamanaev
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Import({SwaggerConfiguration.class})
public @interface EnableSwaggerStarter {
}
