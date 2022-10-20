package com.prog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableCaching 
public class MediShoppingApplication {

	public static void main(String[] args) {
		SpringApplication.run(MediShoppingApplication.class, args);
	}
	
	@Bean
    public CacheManager cacheManager()
    {
    	ConcurrentMapCacheManager  cache = new ConcurrentMapCacheManager("empCacheSpace"); 
    										// parameter empCacheSpace is the name of the cache
    										// in production environment cache manager that is used is:
    									    // GuavaCache, EHCache and RedisCache																					
    	return cache;
    }

}
