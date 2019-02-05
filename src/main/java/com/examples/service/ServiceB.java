package com.examples.service;

public class ServiceB {

    private static Boolean isHealthy = true;

    public String getMessage() {
        System.out.println("Service-B Health status: "+ (isHealthy ?"Healthy":"Unhealthy"));
        if(isHealthy) {
            return "OK";
        } else {
            throw new RuntimeException("Service B is not healthy");
        }
    }

    public static void setIsHealthy(Boolean isHealthy) {
        ServiceB.isHealthy = isHealthy;
    }

}
