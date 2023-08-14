package com.celonis.kvstore.exceptions;

public class KeyNotFoundException extends RuntimeException{
    public KeyNotFoundException(String message) {
        super(message);
    }
}
