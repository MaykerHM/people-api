package com.elotech.people.domain.person.repository;

import java.time.LocalDate;
import java.util.Objects;

public class PersonSearchCriteria {

    private String name;

    private String document;

    private LocalDate birthdateStart;

    private LocalDate birthdateEnd;

    public PersonSearchCriteria(String name, String document, LocalDate birthdateStart, LocalDate birthdateEnd) {
        this.name = name;
        this.document = document;
        this.birthdateStart = birthdateStart;
        this.birthdateEnd = birthdateEnd;
    }

    public String getName() {
        return name;
    }

    public String getDocument() {
        return document;
    }

    public LocalDate getBirthdateStart() {
        return birthdateStart;
    }

    public LocalDate getBirthdateEnd() {
        return birthdateEnd;
    }

    public Boolean hasName() {
        return Objects.nonNull(this.name) && !this.name.isBlank();
    }

    public Boolean hasDocument() {
        return Objects.nonNull(this.document) && !this.document.isBlank();
    }

    public Boolean hasBirthDateStart() {
        return Objects.nonNull(this.birthdateStart);
    }

    public Boolean hasBirthDateEnd() {
        return Objects.nonNull(this.birthdateEnd);
    }
}
