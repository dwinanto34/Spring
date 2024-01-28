package com.app.spring.rest;

import com.app.spring.resilience.RetryDemo;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import jakarta.validation.constraints.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.function.Supplier;

@RestController
@RequestMapping("/resilience")
public class ResilienceController {
    private RetryDemo retryDemo;

    @Autowired
    public ResilienceController(RetryDemo retryDemo) {
        this.retryDemo = retryDemo;
    }

    @GetMapping()
    public String getLogger() {
        // Feature 1: RETRY -- Retry logic is applied to both void and supplier methods.
        // If any exception occurs during code execution, it will attempt retry.
        // For void method:
        // retryDemo.retryRunnable();
        // For method that returns a response:
        // retryDemo.retrySupplier();

        return "resilience";
    }
}
