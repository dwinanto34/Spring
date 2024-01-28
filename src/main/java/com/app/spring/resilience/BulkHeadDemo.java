package com.app.spring.resilience;

import io.github.resilience4j.bulkhead.*;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.concurrent.CompletionStage;
import java.util.function.Supplier;

@Component
public class BulkHeadDemo {
    public void bulkHeadSemaphore() {
        // Having config and registry using bean configuration is always encouraged

        BulkheadConfig bulkheadConfig = BulkheadConfig.custom()
                // Set the maximum of concurrent requests (default is 25)
                .maxConcurrentCalls(5)
                // Set the maximum of waiting duration (default is 0)
                // Here, set to 5 seconds, meaning a request will wait for up to 5 seconds
                // If it has waited for 5 seconds and still reaches the threshold of max concurrent calls, an error will be thrown
                .maxWaitDuration(Duration.ofSeconds(5))
                .build();

        BulkheadRegistry bulkheadRegistry = BulkheadRegistry.ofDefaults();
        bulkheadRegistry.addConfiguration("config", bulkheadConfig);

        Bulkhead bulkhead = bulkheadRegistry.bulkhead("demo", "config");

        for (int i=0; i<100; i++) {
            Runnable runnable = Bulkhead.decorateRunnable(bulkhead, () -> heavyProcess());
            new Thread(runnable).start();
        }
    }

    public void bulkHeadThreadPool() {
        // Having config and registry using bean configuration is always encouraged

        ThreadPoolBulkheadConfig threadPoolBulkheadConfig = ThreadPoolBulkheadConfig.custom()
                // Set the maximum core thread pool size (default is the available processors of the machine)
                .maxThreadPoolSize(5)
                // Set the maximum thread pool size (default is the available processors of the machine - 1)
                .coreThreadPoolSize(5)
                // Set the queue capacity (default is 100)
                .queueCapacity(1)
                // Set the keep-alive duration of a thread (default is 20 milliseconds)
                .keepAliveDuration(Duration.ofSeconds(1))
                .build();

        ThreadPoolBulkheadRegistry threadPoolBulkheadRegistry = ThreadPoolBulkheadRegistry.ofDefaults();
        threadPoolBulkheadRegistry.addConfiguration("config", threadPoolBulkheadConfig);
        ThreadPoolBulkhead threadPoolBulkhead = threadPoolBulkheadRegistry.bulkhead("demo", "config");

        for (int i=0; i<100; i++) {
            Supplier<CompletionStage<Void>> runnable = ThreadPoolBulkhead.decorateRunnable(threadPoolBulkhead, () -> heavyProcess());
            runnable.get();
        }
    }

    private void heavyProcess() {
        System.out.println("Doing heavy process");
        try {
            Thread.sleep(50000L);
        } catch (InterruptedException e) {
            // do nothing
        }
    }
}
