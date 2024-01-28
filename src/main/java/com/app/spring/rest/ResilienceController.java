package com.app.spring.rest;

import com.app.spring.resilience.BulkHeadDemo;
import com.app.spring.resilience.RateLimiterDemo;
import com.app.spring.resilience.RetryDemo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/resilience")
public class ResilienceController {
    private RetryDemo retryDemo;
    private RateLimiterDemo rateLimiterDemo;
    private BulkHeadDemo bulkHeadDemo;

    @Autowired
    public ResilienceController(
            RetryDemo retryDemo,
            RateLimiterDemo rateLimiterDemo,
            BulkHeadDemo bulkHeadDemo
    ) {
        this.retryDemo = retryDemo;
        this.rateLimiterDemo = rateLimiterDemo;
        this.bulkHeadDemo = bulkHeadDemo;
    }

    @GetMapping()
    public String getLogger() {
        // Feature 1: RETRY -- Retry logic is applied to both void and supplier methods.
        // If any exception occurs during code execution, it will attempt retry.
        // For void method:
        // retryDemo.retryRunnable();
        // For method that returns a response:
        // retryDemo.retrySupplier();

        // Feature 2: RATE LIMITER -- Limits requests within a specific time range.
        // For example, if the maximum is set to 100 requests per minute, the 101st request will be denied.
        // rateLimiterDemo.rateLimiter();

        // Feature 3: BULK HEAD -- Limits the number of concurrent requests
        // bulkHeadDemo.bulkHeadSemaphore();
        // bulkHeadDemo.bulkHeadThreadPool();

        return "resilience";
    }
}
