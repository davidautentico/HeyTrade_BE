package com.drosa.heytrade.infrastructure.configuration;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class AppConfiguration {

  @Bean
  public CacheManager cacheManager() {
    return new ConcurrentMapCacheManager(
        CacheNames.POKEMON_CACHE,
        CacheNames.POKEMON_PAGEABLE_LIST_CACHE
    );
  }
}
