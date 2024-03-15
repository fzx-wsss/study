package com.wsss.basic.test;

import org.apache.poi.ss.formula.functions.T;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class CompletableFutureTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        CompletableFuture<T> completableFuture = new CompletableFuture<>();
        completableFuture.get(3, TimeUnit.SECONDS);
        System.out.println("end");
    }
}
