package com.app.spring.resilience;

import com.app.spring.config.ResilienceRetryConfig;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EventPublisherDemo {
    private final RetryRegistry retryRegistry;
    private final RetryConfig retryConfig;

    @Autowired
    public EventPublisherDemo(RetryRegistry retryRegistry, RetryConfig retryConfig) {
        this.retryRegistry = retryRegistry;
        this.retryConfig = retryConfig;
    }

    public void eventPublisher() {
        // Subscribe to events on the RetryRegistry to detect changes in Retry configurations
        retryRegistry.getEventPublisher().onEntryAdded(event -> System.out.println("Entry added"));
        retryRegistry.getEventPublisher().onEntryRemoved(event -> System.out.println("Entry removed"));
        retryRegistry.getEventPublisher().onEntryReplaced(event -> System.out.println("Entry replaced"));

        Retry retry = retryRegistry.retry("demo", ResilienceRetryConfig.REGISTRY_CONFIG_NAME);

        // Subscribe to events on the Retry instance to detect Retry-specific events
        retry.getEventPublisher().onRetry(event -> System.out.println("Attempting retry"));
        retry.getEventPublisher().onSuccess(event -> System.out.println("Success"));
        retry.getEventPublisher().onError(event -> System.out.println("Error"));

        Runnable runnable = Retry.decorateRunnable(retry, () -> calculate());
        runnable.run();
    }

    private void calculate() {
        System.out.println("Run calculation process");
        throw new RuntimeException();
    }
}
