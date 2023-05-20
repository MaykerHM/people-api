package com.elotech.people.domain.person;


import com.elotech.people.domain.contact.Contact;
import com.elotech.people.domain.contact.dto.ContactDTO;
import com.elotech.people.domain.person.exception.InvalidBirthdateException;
import com.elotech.people.domain.person.exception.InvalidDocumentException;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.lang.Integer.parseInt;

@Entity
@Table(name="PERSON")
public class Person {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotNull
    @Column(length = 50)
    private String name;

    @NotNull
    @Column(length = 11)
    private String document;

    @NotNull
    @Column
    private LocalDate birthdate;

    @NotEmpty
    @OneToMany(mappedBy="person", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Contact> contacts;

    public static Person of(String name, String document, LocalDate birthdate, List<ContactDTO> contacts) {
        Person person = new Person();
        String  onlyDigitsDocument= document.replaceAll("\\D", "");
        if (!isValidDocument(onlyDigitsDocument)) {
            throw new InvalidDocumentException();
        }
        if (!isValidBirthDate(birthdate)) {
            throw new InvalidBirthdateException();
        }
        person.name = name;
        person.document = onlyDigitsDocument;
        person.birthdate = birthdate;
        person.contacts = contacts.stream()
                .map(contactDTO -> Contact.of(contactDTO, person))
                .collect(Collectors.toList());
        return person;
    }

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

    public List<Contact> getContacts() {
        return contacts;
    }

    public static boolean isValidDocument(String document) {
        if (document.length() != 11) {
            return  false;
        }

        var sum = 0;
        if (Objects.equals(document, "00000000000")) {
            return false;
        }

        for (int i = 1; i <= 9; i++) {
            sum = sum + parseInt(document.substring(i - 1, i)) * (11 - i);
        }

        var remainder = (sum * 10) % 11;
        if (remainder == 10) {
            remainder = 0;
        }

        if (remainder != parseInt(document.substring(9, 10))) {
            return false;
        }

        sum = 0;
        for (int i = 1; i <= 10; i++) {
            sum = sum + parseInt(document.substring(i - 1, i)) * (12 - i);
        }

        remainder = (sum * 10) % 11;
        if (remainder == 10) {
            remainder = 0;
        }

        return remainder == parseInt(document.substring(10, 11));
    }

    public static Boolean isValidBirthDate(LocalDate birthdate) {
        return birthdate.isBefore(LocalDate.now());
    }
}
