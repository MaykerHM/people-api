package com.elotech.people.domain.person.exception;

public class InvalidBirthdateException extends IllegalArgumentException {
    public InvalidBirthdateException() {
        super("Invalid Birthdate");
    }
}
