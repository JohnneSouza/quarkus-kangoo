package com.easy.ecomm.core;

public class AccountAlreadyActiveException extends RuntimeException{

    public AccountAlreadyActiveException(String message) {
        super(message);
    }
}
