package com.easy.ecomm.core;

public class InvalidActivationKeyException extends RuntimeException{

    public InvalidActivationKeyException(String message) {
        super(message);
    }
}
