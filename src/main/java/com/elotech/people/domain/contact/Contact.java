package com.elotech.people.domain.contact;

import com.elotech.people.domain.contact.dto.ContactDTO;
import com.elotech.people.domain.contact.dto.ContactUpdateDTO;
import com.elotech.people.domain.contact.exception.InvalidEmailException;
import com.elotech.people.domain.person.Person;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;
import java.util.UUID;
import java.util.regex.Pattern;

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

    public static Contact of (ContactDTO dto, Person person) {
        Contact contact = new Contact();
        if(!Contact.isValidEmail(dto.getEmail())) {
            throw new InvalidEmailException();
        }
        contact.name = dto.getName();
        contact.phoneNumber = dto.getPhoneNumber();
        contact.email = dto.getEmail();
        contact.person = person;
        return contact;
    }

    public static Contact update(Contact contact, ContactUpdateDTO contactUpdateDTO) {
        if (Objects.nonNull(contactUpdateDTO.getName())) {
            contact.name = contactUpdateDTO.getName();
        }
        if (Objects.nonNull(contactUpdateDTO.getEmail())) {
            if(!Contact.isValidEmail(contactUpdateDTO.getEmail())) {
                throw new InvalidEmailException();
            }
            contact.email = contactUpdateDTO.getEmail();
        }
        if (Objects.nonNull(contactUpdateDTO.getPhoneNumber())) {
            contact.phoneNumber = contactUpdateDTO.getPhoneNumber();
        }
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

    public static Boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile("^[\\-!#$%&'*+/0-9=?A-Z^_a-z`{|}~](\\.?[\\-!#$%&'*+/0-9=?A-Z^_a-z`{|}~])*@[a-zA-Z0-9](-*\\.?[a-zA-Z0-9])*\\.[a-zA-Z](-?[a-zA-Z0-9])+$");
        return pattern.matcher(email).matches();
    }
}
