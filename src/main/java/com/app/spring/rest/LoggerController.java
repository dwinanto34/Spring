package com.app.spring.rest;

import com.app.spring.model.response.OrderResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/logger")
@Slf4j
public class LoggerController {
    private static final Logger logger = LoggerFactory.getLogger(LoggerController.class);

    @GetMapping()
    public String getLogger() {
        // Logging levels:
        // 1. TRACE
        // 2. DEBUG
        // 3. INFO
        // 4. WARN
        // 5. ERROR
        // 6. OFF (to disable the logging)
        // print plain string
        logger.info("Log for getLogger API");

        String string = "level";
        Integer integer = 1;
        // print variables
        logger.info("{} {}", string, integer);

        // print stack trace
        logger.info("NPE: ", new NullPointerException());

        try {
            OrderResponse orderResponse = null;
            // expecting a ¥ null pointer exception
            orderResponse.getOrderAmount();
        } catch (Exception e) {
            // log the error message (without the stack trace)
            logger.error("An error occurred: " + e.getMessage());
            // log the stack trace
            logger.error("Stack trace: ", e);
        }

        // Lombok also provides the Slf4j annotation that we could use
        // By using that annotation, we don't need to define a new logger variable anymore
        // As the annotation could detect the class name
        log.error("Lombok Slf4j annotation");

        return "logger";
    }
}
