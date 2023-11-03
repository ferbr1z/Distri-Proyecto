package com.example.sdfernandobrizuela.utils;

import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;

import java.time.Duration;

@Configuration
public class CacheConfig {

    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory rf){
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(rf);
        return redisTemplate;
    }

    @Bean
    public RedisCacheConfiguration cacheConfiguration() {
        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(60))
                .serializeValuesWith(SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
    }

    @Bean
    public RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer() {
        return (builder) -> builder

                .withCacheConfiguration("clientesDetallesList",
                        RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(10)).serializeValuesWith(SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer())))
                .withCacheConfiguration("proveedoresList",
                        RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(20)).serializeValuesWith(SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer())))
                .withCacheConfiguration("proveedoresDetallesList",
                        RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(10)).serializeValuesWith(SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer())))
                .withCacheConfiguration("sd::clienteItem",
                        RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(5)).serializeValuesWith(SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer())))
                .withCacheConfiguration("sd::clienteDetalleItem",
                        RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(5)).serializeValuesWith(SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer())))
                .withCacheConfiguration("sd::proveedorItem",
                        RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(5)).serializeValuesWith(SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer())))
                .withCacheConfiguration("sd::proveedorDetalleItem",
                        RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(5)).serializeValuesWith(SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer())));
    }
}
