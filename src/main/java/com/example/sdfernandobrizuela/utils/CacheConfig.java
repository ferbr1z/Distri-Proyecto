package com.example.sdfernandobrizuela.utils;

import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;

import java.time.Duration;

@Configuration
public class CacheConfig {
    @Bean
    public RedisCacheConfiguration cacheConfiguration() {
        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(60))
                .disableCachingNullValues()
                .serializeValuesWith(SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
    }

    @Bean
    public RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer() {
        return (builder) -> builder
                .withCacheConfiguration("clientes",
                        RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(20)).serializeValuesWith(SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer())))
                .withCacheConfiguration("clientesDetalles",
                        RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(10)).serializeValuesWith(SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer())))
                .withCacheConfiguration("proveedores",
                        RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(20)).serializeValuesWith(SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer())))
                .withCacheConfiguration("proveedoresDetalles",
                        RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(10)).serializeValuesWith(SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer())))
                .withCacheConfiguration("cliente",
                        RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(5)).serializeValuesWith(SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer())))
                .withCacheConfiguration("clienteDetalle",
                        RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(5)).serializeValuesWith(SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer())))
                .withCacheConfiguration("proveedor",
                        RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(5)).serializeValuesWith(SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer())))
                .withCacheConfiguration("proveedorDetalle",
                        RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(5)).serializeValuesWith(SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer())));
    }
}
