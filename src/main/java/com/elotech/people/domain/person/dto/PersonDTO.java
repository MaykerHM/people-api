package com.elotech.people.domain.person.dto;

import com.elotech.people.domain.contact.dto.ContactDTO;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.List;

public class PersonDTO {

    @NotNull
    private String name;

    @NotNull
    private String document;

    @NotNull
    private LocalDate birthdate;

    @NotEmpty
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
}
