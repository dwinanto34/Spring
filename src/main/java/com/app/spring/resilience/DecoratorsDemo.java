package com.app.spring.resilience;

import io.github.resilience4j.bulkhead.Bulkhead;
import io.github.resilience4j.bulkhead.ThreadPoolBulkhead;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.decorators.Decorators;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.retry.Retry;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Component
public class DecoratorsDemo {
    public void decorators() {
        Runnable runnable = Decorators.ofRunnable(() -> calculate())
                .withRetry(Retry.ofDefaults("demo-retry"))
                .withRateLimiter(RateLimiter.ofDefaults("demo-rate-limiter"))
                .withBulkhead(Bulkhead.ofDefaults("demo-bulk-head"))
                .withCircuitBreaker(CircuitBreaker.ofDefaults("demo-circuit-breaker"))
                .decorate();

        Supplier<String> supplier = Decorators.ofSupplier(() -> calculateAndReturnResponse())
                .withRetry(Retry.ofDefaults("demo-retry"))
                // if the execution fails, it will do the fallback instead of throw exception
                .withFallback(throwable -> "Hello fallback")
                .decorate();
    }

    private void calculate() {
        System.out.println("Running calculation process");
    }

    private String calculateAndReturnResponse() {
        System.out.println("Run calculateAndReturnResponse process");
        throw new RuntimeException("error supplier");
    }
}
