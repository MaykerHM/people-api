package com.elotech.people.domain.person.exception;

public class InvalidDocumentException extends IllegalArgumentException {
    public InvalidDocumentException() {
        super("Invalid Document");
    }
}
