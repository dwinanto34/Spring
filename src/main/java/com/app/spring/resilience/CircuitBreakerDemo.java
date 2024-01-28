package com.app.spring.resilience;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class CircuitBreakerDemo {
    public void circuitBreaker() {
        // Source: https://resilience4j.readme.io/docs/circuitbreaker

        // The initial state is CLOSED.
        // CLOSED means all requests will proceed without interruption.
        // OPEN means no requests will proceed.
        // HALF_OPEN means only a limited number of requests will proceed.

        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()

                // Set the failure rate threshold to 50.1% (Default is 50%)
                // When it exceeds the threshold, the circuit breaker transitions from CLOSED to OPEN
                .failureRateThreshold(50.1f)

                // Set the sliding window type to TIME_BASED (Default is COUNT_BASED)
                // TIME_BASED counts by time range, e.g., 1 minute.
                // COUNT_BASED counts by the number of processes, e.g., 100 requests.
                .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.TIME_BASED)

                // Sliding window size depends on the type (Default is 100)
                // If the type is TIME_BASED, 60 means it will calculate by 60 seconds.
                // If the type is COUNT_BASED, 60 means it will calculate the rate by 60 requests.
                .slidingWindowSize(60)

                // Set the minimum number of calls (Default is 100)
                // Configures the minimum number of calls required (per sliding window period) before the CircuitBreaker calculates the error rate or slow call rate.
                // For example, if minimumNumberOfCalls is 10, at least 10 calls must be recorded before the failure rate can be calculated.
                // If only 9 calls have been recorded, the CircuitBreaker will not transition to open even if all 9 calls have failed.
                .minimumNumberOfCalls(10)

                // Waiting duration from OPEN to HALF_OPEN (Default is 60000 ms)
                .waitDurationInOpenState(Duration.ofSeconds(100))

                // Number of permitted calls in HALF_OPEN state for trial requests to pass through (Default is 10)
                // If all permitted trial requests are successful, the circuit breaker transitions from HALF_OPEN to CLOSED
                .permittedNumberOfCallsInHalfOpenState(50)

                // Set the maximum waiting duration in HALF_OPEN state before transitioning to OPEN (Default is 0, meaning waiting forever).
                // If set to 0 (forever):
                //   - Worst case: The permitted calls keep failing, and it will remain in the HALF_OPEN state forever.
                //   - Best case: If the permitted calls are successful, it will transition to CLOSED.
                // If set to 1 minute:
                //   - It defines the maximum duration the Circuit Breaker can stay in HALF_OPEN state.
                //   - Worst case: If the circuit breaker stays in HALF_OPEN for 1 minute successfully completing the permitted calls, it will automatically transition to OPEN.
                //   - Best case: If the permitted calls are resolved within 1 minute, it will transition to CLOSED.
                .maxWaitDurationInHalfOpenState(Duration.ofMinutes(1))

                // Set the threshold for considering a call as slow (Default is 60000 ms).
                // A request is considered slow when its processing time exceeds 100 seconds.
                .slowCallDurationThreshold(Duration.ofSeconds(100))

                // Set the threshold for the rate of slow calls (Default is 100).
                // If the rate of slow calls exceeds the threshold, then the circuit breaker transitions to OPEN.
                .slowCallRateThreshold(100)
                .build();

        CircuitBreakerRegistry circuitBreakerRegistry = CircuitBreakerRegistry.ofDefaults();
        circuitBreakerRegistry.addConfiguration("config", circuitBreakerConfig);

        CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker("demo", "config");

        for (int i=0; i<10000; i++) {
            try {
                Runnable runnable = CircuitBreaker.decorateRunnable(circuitBreaker, () -> process());
                runnable.run();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void process() {
        throw new RuntimeException();
    }
}
