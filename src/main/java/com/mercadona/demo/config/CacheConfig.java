package com.mercadona.demo.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public CacheManager cacheManager() {
        // Crear la configuración de Caffeine
        Caffeine<Object, Object> caffeineCacheBuilder = Caffeine.newBuilder()
                .expireAfterWrite(10, TimeUnit.MINUTES)  // Expirar después de 10 minutos sin ser utilizado
                .maximumSize(100);                       // Límite de 100 entradas en la caché

        // Configurar el CacheManager con la instancia de Caffeine
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCaffeine(caffeineCacheBuilder);

        return cacheManager;
    }
}
