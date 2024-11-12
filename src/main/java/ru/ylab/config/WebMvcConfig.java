package ru.ylab.config;

import java.text.SimpleDateFormat;
import java.util.List;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Class for customizing Spring MVC configuration.
 *
 * @author azatyamanaev
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * Adds custom {@link HttpMessageConverter}s.
     *
     * @param converters list of converters to use in application
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new ByteArrayHttpMessageConverter());

        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder()
                .indentOutput(true)
                .dateFormat(new SimpleDateFormat("yyyy-MM-dd"))
                .failOnUnknownProperties(false)
                .failOnEmptyBeans(false)
                .modulesToInstall(new JavaTimeModule());

        converters.add(new MappingJackson2HttpMessageConverter(builder.build()));
    }
}
