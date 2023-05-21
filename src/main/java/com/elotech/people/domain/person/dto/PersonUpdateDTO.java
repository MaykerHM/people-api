package com.elotech.people.domain.person.dto;

import java.time.LocalDate;

public class PersonUpdateDTO {

    private String name;

    private String document;

    private LocalDate birthdate;


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
