package com.elotech.people.domain.contact;

import com.elotech.people.domain.contact.dto.ContactDTO;
import com.elotech.people.domain.person.Person;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
public class ContactTest {

    String VALID_EMAIL = "fulano@email.com";

    String INVALID_EMAIL = "fulano.@email.com";

    String NAME = "Fake Name";

    String DOCUMENT = "76562599024";

    LocalDate BIRTHDATE = LocalDate.parse("1991-08-14");

    @Test
    public void isValidEmail_whenIsValidEmail_shouldReturnTrue() {
        Boolean isValid = Contact.isValidEmail(VALID_EMAIL);
        assertTrue(isValid);
    }

    @Test
    public void isValidEmail_whenIsNotValidEmail_shouldReturnFalse() {
        Boolean isValid = Contact.isValidEmail(INVALID_EMAIL);
        assertFalse(isValid);
    }

    @Test
    public void of_whenIsNotValidEmail_shouldThrow() {
        ContactDTO validContactDTO = ContactDTO.of("Fake Contact Name One", "449994564654", VALID_EMAIL);
        ContactDTO invalidContactDTO = ContactDTO.of("Fake Contact Name Two", "44999876656", INVALID_EMAIL);
        Person person = Person.of(NAME, DOCUMENT, BIRTHDATE, List.of(validContactDTO));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Contact.of(invalidContactDTO, person);
        });

        assertEquals(exception.getMessage(), "Invalid Email");
    }
}
