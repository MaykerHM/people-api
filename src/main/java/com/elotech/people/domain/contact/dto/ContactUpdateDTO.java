package com.elotech.people.domain.contact.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public class ContactUpdateDTO {

    @NotNull
    private UUID id;

    @NotNull
    @Size(max = 50)
    private String name;

    @NotNull
    @Size(max = 20)
    private String phoneNumber;

    @NotNull
    @Size(max = 70)
    private String email;

    public static ContactUpdateDTO of(UUID id, String name, String phoneNumber, String email) {
        ContactUpdateDTO dto = new ContactUpdateDTO();
        dto.id = id;
        dto.name = name;
        dto.phoneNumber = phoneNumber;
        dto.email = email;
        return dto;
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
}
