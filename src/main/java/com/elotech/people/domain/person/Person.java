package com.elotech.people.domain.person;


import com.elotech.people.domain.contact.Contact;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name="PERSON")
public class Person {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotNull
    @Column(nullable = false, length = 50)
    private String name;

    @NotNull
    @Column(nullable = false, length = 11)
    private String document;

    @NotNull
    @Column(nullable = false)
    private LocalDate birthdate;

    @NotEmpty
    @OneToMany(mappedBy="person", fetch = FetchType.LAZY)
    private Set<Contact> contacts;

}
