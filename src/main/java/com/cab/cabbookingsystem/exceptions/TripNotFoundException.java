package com.cab.cabbookingsystem.exceptions;

public class TripNotFoundException extends RuntimeException {
    public TripNotFoundException(String e) {
        super(e);
    }
}
