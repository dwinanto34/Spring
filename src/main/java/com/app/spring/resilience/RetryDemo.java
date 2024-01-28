package com.app.spring.resilience;

import com.app.spring.config.ResilienceRetryConfig;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.function.Supplier;

@Component
public class RetryDemo {
    private final RetryRegistry retryRegistry;
    private final RetryConfig retryConfig;

    @Autowired
    public RetryDemo(RetryRegistry retryRegistry, RetryConfig retryConfig) {
        this.retryRegistry = retryRegistry;
        this.retryConfig = retryConfig;
    }

    public void retryRunnable() {
//        There are 2 ways to declare a retry
//        1. Using Retry.of
//        Retry retry = Retry.of("demo", retryConfig);

//        2. Using Registry (Recommended approach)
//        When using Registry, we could initiate new retry without creating new one if they are exist (Singleton)
        Retry retry = retryRegistry.retry("demo", ResilienceRetryConfig.REGISTRY_CONFIG_NAME);
        Runnable runnable = Retry.decorateRunnable(retry, () -> calculate());
        runnable.run();

        // Get metric data from Retry
        long failedCallsWithoutRetryAttempt = retry.getMetrics().getNumberOfFailedCallsWithoutRetryAttempt();
        long failedCallsWithRetryAttempt = retry.getMetrics().getNumberOfFailedCallsWithRetryAttempt();
        long successfulCallsWithoutRetryAttempt = retry.getMetrics().getNumberOfSuccessfulCallsWithoutRetryAttempt();
        long successfulCallsWithRetryAttempt = retry.getMetrics().getNumberOfSuccessfulCallsWithRetryAttempt();
    }

    private void calculate() {
        System.out.println("Run calculation process");
        throw new RuntimeException();
    }

    public void retrySupplier() {
        // Create a retry instance with default settings
        Retry retry = Retry.ofDefaults("demo");

        Supplier<String> supplier = Retry.decorateSupplier(retry, () -> calculateAndReturnResponse());
        String response = supplier.get();
        System.out.println(response);
    }

    private String calculateAndReturnResponse() {
        System.out.println("Run calculateAndReturnResponse process");
        throw new RuntimeException("error supplier");
    }
}
