package org.example.walletapp.web.rest.errors;

public class InternalServerErrorException extends RuntimeException {
    public InternalServerErrorException(String s) {
        super(s);
    }
}
