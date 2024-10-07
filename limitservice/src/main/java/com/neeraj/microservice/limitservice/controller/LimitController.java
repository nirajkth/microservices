package com.neeraj.microservice.limitservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neeraj.microservice.limitservice.bean.Limit;
import com.neeraj.microservice.limitservice.configuration.Configuration;

@RestController
public class LimitController {

    @Autowired
    private Configuration configuration;

    @GetMapping("/limits")
    public Limit getLimits() {
        return new Limit(configuration.getMinimum(), configuration.getMaximum());
    }
}
