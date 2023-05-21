package com.elotech.people.domain.contact.exception;

public class ContactNotFoundByIdException extends IllegalArgumentException {
    public ContactNotFoundByIdException() {
        super("Contact not found by ID");
    }
}
