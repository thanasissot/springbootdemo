package com.asot.springbootdemo.configuration;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheConfig {
    // Default configuration is sufficient for simple use-cases.
}
