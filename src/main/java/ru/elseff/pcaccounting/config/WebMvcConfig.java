package ru.elseff.pcaccounting.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Slf4j
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("#{'${allowed.origins}'.split(' ')}")
    List<String> origins;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(origins.toArray(new String[0]))
                .allowedHeaders("*")
                .allowedMethods("*")
                .exposedHeaders("Content-Type", "Authorization");
        log.info("Allowed origins --- " + String.join(", ", origins));
    }
}
