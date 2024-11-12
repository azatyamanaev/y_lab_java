package ru.ylab.auditstarter.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.ylab.auditstarter.aspects.MetricsAspect;

/**
 * Class containing application metrics configuration.
 *
 * @author azatyamanaev
 */
@Configuration
public class MetricsConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public MetricsAspect metricsAspect() {
        return new MetricsAspect();
    }
}
