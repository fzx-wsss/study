package com.wsss.basic.test;

public class Temp {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        long end = System.currentTimeMillis();
        int count = 0;
        while(end - start < 30) {
            end = System.currentTimeMillis();
            count ++;
        }
        System.out.println(end-start);
        System.out.println(count);
    }
}
