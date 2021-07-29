package dev.kangoo.core.email;

public class EmailTakenException extends RuntimeException{

    public EmailTakenException(String message) {
        super(message);
    }
}
