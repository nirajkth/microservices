package com.neeraj.microservices.currency_conversion_service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// @FeignClient(name = "currency-exchange", url = "http://127.0.0.1:8000")
@FeignClient(name = "currency-exchange")
public interface CurrencyConversionProxy {
    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public CurrencyConversion retrieveExchangeValue(@PathVariable String from,
            @PathVariable String to);

}
