package dev.kangoo.core;

public class AccountAlreadyActiveException extends RuntimeException{

    public AccountAlreadyActiveException(String message) {
        super(message);
    }
}
