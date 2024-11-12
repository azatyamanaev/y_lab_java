package ru.spring.auditstarter.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;
import ru.spring.auditstarter.aspects.MetricsAspect;
import ru.spring.auditstarter.config.MetricsConfiguration;

/**
 * Annotation for enabling {@link MetricsAspect}.
 *
 * @author azatyamanaev
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Import({MetricsConfiguration.class})
public @interface EnableMetrics {
}
