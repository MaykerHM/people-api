package com.elotech.people.domain.person.dto;

import com.elotech.people.domain.contact.Contact;
import com.elotech.people.domain.person.Person;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class PersonFindByIdDTO {

    private UUID id;

    private String name;

    private String document;

    private LocalDate birthdate;

    private List<Contact> contacts;

    public static PersonFindByIdDTO of(Person person) {
        PersonFindByIdDTO dto = new PersonFindByIdDTO();
        dto.id = person.getId();
        dto.name = person.getName();
        dto.document = person.getDocument();
        dto.birthdate = person.getBirthdate();
        dto.contacts = person.getContacts();
        return dto;
    }

    public UUID getId() { return id; }

    public String getName() {
        return name;
    }

    public String getDocument() {
        return document;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public List<Contact> getContacts() { return contacts; }
}
