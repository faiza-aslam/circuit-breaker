package com.examples.breaker;

import com.examples.service.ServiceB;

import java.time.Duration;
import java.time.Instant;

public class CircuitBreaker {

    private int failureThreshold;
    private int retryTimePeriod;
    private CircuitState state;
    private Instant lastFailureTime;
    private int failureCount = 0; //current failure count

    private CircuitBreaker(Builder builder) {
        this.failureThreshold=builder.failureThreshold;
        this.retryTimePeriod=builder.retryTimePeriod;
        this.state = CircuitState.CLOSED;
    }

    public void callB() {
        setCircuitState();
        if(state==CircuitState.CLOSED || state==CircuitState.HALF_OPEN) {
            callService();
        } else {
            System.out.println("Not calling Service-B. CircuitState="+state);
        }

    }

    private void callService() {
        ServiceB serviceB = new ServiceB();
        try {
            String message = serviceB.getMessage();
            System.out.println(message);
            if(state!=CircuitState.CLOSED) {
                reset();
            }

        } catch (RuntimeException ex) {
            ++failureCount;
            lastFailureTime=Instant.now();
            ex.printStackTrace();
        }
    }

    private void reset() {
        this.state = CircuitState.CLOSED;
        this.failureCount = 0;
        this.lastFailureTime = null;
    }

    private void setCircuitState() {
        if(failureCount >= failureThreshold) {
            if(lastFailureTime!=null && Duration.between(lastFailureTime, Instant.now()).getSeconds() > retryTimePeriod) {
                this.state = CircuitState.HALF_OPEN;
            } else {
                this.state = CircuitState.OPEN;
            }
        } else {
            this.state = CircuitState.CLOSED;
        }
    }

    public static class Builder {

        private int failureThreshold=1; //failure threshold after which circuit will be Opened
        private int retryTimePeriod=60; //duration in seconds

        public static Builder newBuilder() {
            return new Builder();
        }

        public Builder setFailureThreshold(int failureThreshold) {
            this.failureThreshold = failureThreshold;
            return this;
        }

        public Builder setRetryTimePeriod(int retryTimePeriod) {
            this.retryTimePeriod = retryTimePeriod;
            return this;
        }

        public CircuitBreaker build() {
            return new CircuitBreaker(this);
        }
    }
}
