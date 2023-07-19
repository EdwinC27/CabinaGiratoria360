package com.api.cabina_giratoria.configuracion;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ConfigCors {

    @Value("${frontend.url}")
    String FRONTEND_URL;

    @Value("${frontend.url.public}")
    String FRONTEND_URL_PUBLIC;

    @Value("${frontene.url.local}")
    String FRONTEND_URL_LOCAL;

    @Bean
    public WebMvcConfigurer configuracionCors() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins(FRONTEND_URL, FRONTEND_URL_PUBLIC, FRONTEND_URL_LOCAL)
                        .allowedMethods("GET", "POST");
                //.allowedHeaders()
                //.exposedHeaders()
            }
        };
    }
}