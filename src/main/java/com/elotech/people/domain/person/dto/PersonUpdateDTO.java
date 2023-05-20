package com.elotech.people.domain.person.dto;

import com.elotech.people.domain.contact.dto.ContactDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class PersonUpdateDTO {

    private String name;

    private String document;

    private LocalDate birthdate;

    private List<ContactDTO> contacts;

    public String getName() {
        return name;
    }

    public String getDocument() {
        return document;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public List<ContactDTO> getContacts() {
        return contacts;
    }

    public static  Boolean hasContacts(PersonUpdateDTO personUpdateDTO) {
        return Objects.nonNull(personUpdateDTO.getContacts()) && !personUpdateDTO.getContacts().isEmpty();
    }
}
