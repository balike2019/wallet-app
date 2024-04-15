package org.example.walletapp.web.errors;

public class UsernameAlreadyUsedException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public UsernameAlreadyUsedException() {
        super("Username already used");
    }

    public UsernameAlreadyUsedException(String msg) {
        super(msg);
    }
}
