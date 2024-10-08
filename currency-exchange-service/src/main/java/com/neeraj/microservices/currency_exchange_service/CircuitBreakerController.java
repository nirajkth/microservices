package com.neeraj.microservices.currency_exchange_service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.retry.annotation.Retry;

@RestController
public class CircuitBreakerController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CircuitBreakerController.class);

    @GetMapping("/sample-api")
    @Retry(name = "sample-api", fallbackMethod = "hardcodedResponse")
    public String sampleApi() {
        LOGGER.info("Sample API is called");

        var forEntity = new RestTemplate().getForEntity("http://localhost:8080/some-dummy-url",
                String.class);

        return forEntity.getBody();
    }

    private String hardcodedResponse(Exception ex) {
        return "hardcoded-response";
    }
}