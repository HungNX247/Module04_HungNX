package com.codegym.curencyconverter.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CurrencyService {
    private static final Map<String, Double> RATES = new HashMap<>();
    static {
        RATES.put("USD",1.0);
        RATES.put("VND",26000.0);
        RATES.put("JPY",150.0);
        RATES.put("EUR",0.9);
    }

    public double convert(String from,String to, double amount) {
        double fromRate = RATES.get(from);
        double toRate = RATES.get(to);
        return amount * (toRate/fromRate);
    }

    public Map<String, Double> getRates() {
        return RATES;
    }
}
