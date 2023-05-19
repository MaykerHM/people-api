package com.elotech.people.domain.contact;

import com.elotech.people.domain.person.Person;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name="CONTACT")
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 20)
    private String phoneNumber;


    @Column(nullable = false, length = 70)
    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id")
    private Person person;
}
