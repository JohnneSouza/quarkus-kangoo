package com.easy.ecomm.exceptions;

public class InvalidActivationKeyException extends RuntimeException{

    public InvalidActivationKeyException(String message) {
        super(message);
    }
}
