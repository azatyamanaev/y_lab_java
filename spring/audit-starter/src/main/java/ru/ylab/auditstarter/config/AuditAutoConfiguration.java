package ru.ylab.auditstarter.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.ylab.auditstarter.aspects.AuditAspect;

@Configuration
@ConditionalOnClass(AuditAspect.class)
public class AuditAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public AuditAspect auditAspect() {
        return new AuditAspect();
    }
}
