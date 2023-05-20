package com.elotech.people.domain.person.exception;

public class PersonNotFoundByIdException extends IllegalArgumentException {
    public PersonNotFoundByIdException() {
        super("Person not found by ID");
    }
}
