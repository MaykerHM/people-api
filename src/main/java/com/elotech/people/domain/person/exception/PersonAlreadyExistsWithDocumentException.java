package com.elotech.people.domain.person.exception;

public class PersonAlreadyExistsWithDocumentException extends IllegalArgumentException {
    public PersonAlreadyExistsWithDocumentException() {
        super("Person already exists with this document");
    }
}
