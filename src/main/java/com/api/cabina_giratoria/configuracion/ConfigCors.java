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

    @Bean
    public WebMvcConfigurer configuracionCors() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins(FRONTEND_URL)
                        .allowedMethods("GET");
                //.allowedHeaders()
                //.exposedHeaders()
            }
        };
    }
}