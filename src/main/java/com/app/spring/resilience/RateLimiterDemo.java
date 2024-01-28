package com.app.spring.resilience;

import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class RateLimiterDemo {
    public void rateLimiter() {
        // Having config and registry using bean configuration is always encouraged

        RateLimiterConfig rateLimiterConfig = RateLimiterConfig.custom()
                // Set the duration time to do counter reset (default is 50 nanoseconds)
                .limitRefreshPeriod(Duration.ofMinutes(1))
                // Set the maximum of requests' counter (default is 50 requests)
                .limitForPeriod(100)
                // Set the timeout of request (default is 5 seconds)
                .timeoutDuration(Duration.ofSeconds(2))
                .build();

        RateLimiterRegistry rateLimiterRegistry = RateLimiterRegistry.ofDefaults();
        rateLimiterRegistry.addConfiguration("config", rateLimiterConfig);

        RateLimiter rateLimiter = rateLimiterRegistry.rateLimiter("demo", "config");

        for (int i=0; i<10000; i++) {
            String index = String.valueOf(i);
            Runnable runnable = RateLimiter.decorateRunnable(rateLimiter, () -> {
                System.out.println(index);
            });
            runnable.run();
        }
    }
}
