package com.elotech.people.domain.contact.exception;

import org.springframework.web.client.HttpClientErrorException;

public class ContactAtLeastOneException extends RuntimeException {
    public ContactAtLeastOneException() {
        super("A person should have at least one contact");
    }
}
