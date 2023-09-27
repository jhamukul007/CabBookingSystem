package com.cab.cabbookingsystem.exceptions;

public class UserAlreadyExistException extends RuntimeException {
    public UserAlreadyExistException(String e) {
        super(e);
    }
}
