package com.app.spring.resilience;

import io.github.resilience4j.timelimiter.TimeLimiter;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import io.github.resilience4j.timelimiter.TimeLimiterRegistry;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Component
public class TimeLimiterDemo {
    public void timeLimiter() throws Exception {
        // Having config and registry using bean configuration is always encouraged

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<String> future = executorService.submit(() -> heavyProcess());

        TimeLimiterConfig timeLimiterConfig = TimeLimiterConfig.custom()
                // Set the timeout duration (Default is 1 second)
                .timeoutDuration(Duration.ofSeconds(2))
                // Set to true if we want to cancel the running future if it exceeds the timeout
                .cancelRunningFuture(true)
                .build();

        TimeLimiterRegistry timeLimiterRegistry = TimeLimiterRegistry.ofDefaults();
        timeLimiterRegistry.addConfiguration("config", timeLimiterConfig);

        TimeLimiter timeLimiter = timeLimiterRegistry.timeLimiter("demo", "config");
        Callable<String> callable = TimeLimiter.decorateFutureSupplier(timeLimiter, () -> future);

        callable.call();
    }

    private String heavyProcess() {
        System.out.println("Doing heavy process");
        try {
            Thread.sleep(50000L);
        } catch (InterruptedException e) {
            // do nothing
        }

        return "result";
    }
}
