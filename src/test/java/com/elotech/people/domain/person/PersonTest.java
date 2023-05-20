package com.elotech.people.domain.person;

import com.elotech.people.domain.contact.Contact;
import com.elotech.people.domain.contact.dto.ContactDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
public class PersonTest {

    String VALID_DOCUMENT = "55990764073";

    String INVALID_DOCUMENT = "94857346234";

    String NAME = "Fake Name";

    LocalDate BIRTHDATE = LocalDate.parse("1991-08-14");

    @Test
    public void isValidDocument_whenIsValidDocument_shouldReturnTrue() {
        Boolean isValid = Person.isValidDocument(VALID_DOCUMENT);
        assertTrue(isValid);
    }

    @Test
    public void isValidDocument_whenIsNotValidDocument_shouldReturnFalse() {
        Boolean isValid = Person.isValidDocument(INVALID_DOCUMENT);
        assertFalse(isValid);
    }

    @Test
    public void of_whenIsNotValidDocument_shouldThrow() {
        ContactDTO validContactDTO = ContactDTO.of("Fake Contact Name", "449994564654", "fulano@email.com");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Person.of(NAME, INVALID_DOCUMENT, BIRTHDATE, List.of(validContactDTO));
        });

        assertEquals(exception.getMessage(), "Invalid Document");
    }
}
