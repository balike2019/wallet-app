package org.example.walletapp.service;

public class UpdateUserException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public UpdateUserException(String userCouldNotBeUpdated) {
        super(userCouldNotBeUpdated);
    }
}
