package com.cab.cabbookingsystem.utils;

public class ConsoleLogger implements Loggable{
    @Override
    public void log(Object o) {
        System.out.println(o);
        System.out.println("-------------------------------------------");
    }
}
