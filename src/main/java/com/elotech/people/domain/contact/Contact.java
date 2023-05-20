package com.elotech.people.domain.contact;

import com.elotech.people.domain.contact.dto.ContactDTO;
import com.elotech.people.domain.person.Person;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@Entity
@Table(name="CONTACT")
public class Contact {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotNull
    @Column(length = 50)
    private String name;

    @NotNull
    @Column(length = 20)
    private String phoneNumber;

    @NotNull
    @Column(length = 70)
    private String email;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id")
    private Person person;

    public static Contact of(ContactDTO dto, Person person) {
        Contact contact = new Contact();
        contact.name = dto.getName();
        contact.phoneNumber = dto.getPhoneNumber();
        contact.email = dto.getEmail();
        contact.person = person;
        return contact;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    @JsonIgnore
    public Person getPerson() {
        return person;
    }
}
