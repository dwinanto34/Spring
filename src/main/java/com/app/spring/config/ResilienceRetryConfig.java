package com.app.spring.config;

import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class ResilienceRetryConfig {
    public static final String REGISTRY_CONFIG_NAME = "config";

    @Bean
    public RetryConfig retryConfig() {
        // Configure retry settings
        RetryConfig retryConfig = RetryConfig.custom()
                // Set the maximum number of retry attempts (default is 3)
                .maxAttempts(5)
                // Set the duration to wait before each retry (default is 500ms)
                .waitDuration(Duration.ofSeconds(2))
                // Specify exceptions to trigger retry;
                // By default, it is empty, which means all exceptions are retried
                .retryExceptions(RuntimeException.class)
                .build();

        return retryConfig;
    }

    @Bean
    public RetryRegistry retryRegistry(RetryConfig retryConfig) {
        RetryRegistry retryRegistry = RetryRegistry.ofDefaults();
        retryRegistry.addConfiguration(REGISTRY_CONFIG_NAME, retryConfig);
        return retryRegistry;
    }
}
