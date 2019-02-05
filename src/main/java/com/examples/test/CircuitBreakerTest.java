package com.examples.test;

import com.examples.service.ServiceA;
import com.examples.service.ServiceB;

import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CircuitBreakerTest {

    public static void main(String[] args) {
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        registerHook(scheduledExecutorService);
        ServiceA serviceA = new ServiceA();
        Runnable runnableTask = serviceA::callB;
        scheduledExecutorService.scheduleAtFixedRate(runnableTask, 1, 5, TimeUnit.SECONDS);

        System.out.println("Enter \n'u' to make ServiceB un-healthy, \n'h' to make it healthy again and \n's' to stop the program\n\n");
        Scanner scanner = new Scanner(System.in);
        String readKey;
        do {
            readKey = scanner.next();
            switch (readKey) {
                case "u":
                    ServiceB.setIsHealthy(false);
                    break;

                case "h":
                    ServiceB.setIsHealthy(true);
                    break;

                default:
                    break;
            }
        } while (!"s".equalsIgnoreCase(readKey));

        scheduledExecutorService.shutdown();
    }

    private static void registerHook(ScheduledExecutorService scheduledExecutorService) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutdown Hook is running !"+scheduledExecutorService.isShutdown());
            if(!scheduledExecutorService.isShutdown()) {
                scheduledExecutorService.shutdown();
            }
        }));
    }
}
