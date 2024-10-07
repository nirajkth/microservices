package com.neeraj.microservices.currency_conversion_service;

import java.math.BigDecimal;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CurrencyConversionController {
        @Autowired
        private CurrencyConversionProxy proxy;

        @GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
        public CurrencyConversion calculateCurrencyConversion(@PathVariable String from,
                        @PathVariable String to, @PathVariable BigDecimal quantity) {

                var uriVariables = new HashMap<String, String>();
                uriVariables.put("from", from);
                uriVariables.put("to", to);

                var responseEntity = new RestTemplate().getForEntity(
                                "http://127.0.0.1:8000/currency-exchange/from/{from}/to/{to}",
                                CurrencyConversion.class, uriVariables);
                var currencyConversion = responseEntity.getBody();
                var conversionMultiple = currencyConversion.getConversionMultiple();

                return new CurrencyConversion(currencyConversion.getId(), from, to, quantity, conversionMultiple,
                                quantity.multiply(conversionMultiple), currencyConversion.getEnvironment());
        }

        @GetMapping("/currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}")
        public CurrencyConversion calculateCurrencyConversionFeign(@PathVariable String from,
                        @PathVariable String to, @PathVariable BigDecimal quantity) {
                var currencyConversion = proxy.retrieveExchangeValue(from, to);
                var conversionMultiple = currencyConversion.getConversionMultiple();

                return new CurrencyConversion(currencyConversion.getId(), from, to, quantity, conversionMultiple,
                                quantity.multiply(conversionMultiple), currencyConversion.getEnvironment());
        }
}
