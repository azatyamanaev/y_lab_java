package ru.ylab.auditstarter.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;
import ru.ylab.auditstarter.config.MetricsConfiguration;

/**
 * Annotation for enabling {@link ru.ylab.auditstarter.aspects.MetricsAspect}.
 *
 * @author azatyamanaev
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Import({MetricsConfiguration.class})
public @interface EnableMetrics {
}
