package com.elotech.people.domain.contact.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ContactDTO {

    @NotNull
    @Size(max = 50)
    private String name;

    @NotNull
    @Size(max = 20)
    private String phoneNumber;

    @NotNull
    @Size(max = 70)
    private String email;

    public static ContactDTO of(String name, String phoneNumber, String email) {
        ContactDTO dto = new ContactDTO();
        dto.name = name;
        dto.phoneNumber = phoneNumber;
        dto.email = email;
        return dto;
    }

    public static ContactDTO of(ContactCreateDTO contactCreateDTO) {
        ContactDTO dto = new ContactDTO();
        dto.name = contactCreateDTO.getName();
        dto.phoneNumber = contactCreateDTO.getPhoneNumber();
        dto.email = contactCreateDTO.getEmail();
        return dto;
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
