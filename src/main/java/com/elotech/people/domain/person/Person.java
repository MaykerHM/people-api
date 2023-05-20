package com.elotech.people.domain.person;


import com.elotech.people.domain.contact.Contact;
import com.elotech.people.domain.contact.dto.ContactDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
    @OneToMany(mappedBy="person", fetch = FetchType.LAZY)
    private List<Contact> contacts;

    public static Person of(String name, String document, LocalDate birthdate, List<ContactDTO> contacts) {
        Person person = new Person();
        person.name = name;
        person.document = document;
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
}
