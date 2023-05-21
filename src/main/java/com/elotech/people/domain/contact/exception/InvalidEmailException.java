package com.elotech.people.domain.contact.exception;

public class InvalidEmailException extends IllegalArgumentException {
    public InvalidEmailException() {
        super("Invalid Email");
    }
}
