package com.easy.ecomm.exceptions;

public class UserAccountAlreadyActiveException extends RuntimeException{

    public UserAccountAlreadyActiveException(String message) {
        super(message);
    }
}
