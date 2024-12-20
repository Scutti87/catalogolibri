package com.rest.catalogolibri.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Configura per tutti gli endpoint
            .allowedOrigins("http://localhost:8081") // Origine del frontend (se stai usando porte diverse o un frontend esterno)
            .allowedMethods("GET", "POST", "PUT", "DELETE") // Metodi HTTP consentiti
            .allowedHeaders("Authorization", "Content-Type") // Headers consentiti
            .allowCredentials(true); // Consenti l'invio di credenziali (come i cookie)
    }
}
