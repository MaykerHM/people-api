package com.elotech.people.domain.person.dto;

import java.time.LocalDate;
import java.util.UUID;

public class PersonUpdateDTO {

    private UUID id;

    private String name;

    private String document;

    private LocalDate birthdate;

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDocument() {
        return document;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }
}
