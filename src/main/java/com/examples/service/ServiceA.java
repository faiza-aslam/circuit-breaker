package com.examples.service;

import com.examples.breaker.CircuitBreaker;

public class ServiceA {

    private CircuitBreaker circuitBreaker = CircuitBreaker.Builder
            .newBuilder()
            .setFailureThreshold(1)
            .setRetryTimePeriod(60)
            .build();

    public void callB() {
        circuitBreaker.callB();
    }

}
