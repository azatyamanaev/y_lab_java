package ru.spring.auditstarter.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.spring.auditstarter.aspects.AuditAspect;
import ru.spring.auditstarter.repository.AuditRepository;

/**
 * Class containing user requests audit configuration.
 *
 * @author azatyamanaev
 */
@Configuration
public class AuditConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public AuditAspect auditAspect(AuditRepository auditRepository) {
        return new AuditAspect(auditRepository);
    }
}
