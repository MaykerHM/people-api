package com.elotech.people.domain.contact.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public class ContactCreateDTO {

    @NotNull
    private UUID personId;

    @NotNull
    @Size(max = 50)
    private String name;

    @NotNull
    @Size(max = 20)
    private String phoneNumber;

    @NotNull
    @Size(max = 70)
    private String email;

    public static ContactCreateDTO of(UUID personId, String name, String phoneNumber, String email) {
        ContactCreateDTO dto = new ContactCreateDTO();
        dto.personId = personId;
        dto.name = name;
        dto.phoneNumber = phoneNumber;
        dto.email = email;
        return dto;
    }

    public UUID getPersonId() {
        return personId;
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
}
