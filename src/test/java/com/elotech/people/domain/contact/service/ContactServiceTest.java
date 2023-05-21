package com.elotech.people.domain.contact.service;

import com.elotech.people.domain.contact.Contact;
import com.elotech.people.domain.contact.dto.ContactCreateDTO;
import com.elotech.people.domain.contact.dto.ContactDTO;
import com.elotech.people.domain.contact.dto.ContactUpdateDTO;
import com.elotech.people.domain.contact.exception.ContactAtLeastOneException;
import com.elotech.people.domain.contact.exception.ContactNotFoundByIdException;
import com.elotech.people.domain.contact.repository.ContactRepository;
import com.elotech.people.domain.person.Person;
import com.elotech.people.domain.person.exception.PersonNotFoundByIdException;
import com.elotech.people.domain.person.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class ContactServiceTest {

    @InjectMocks
    private ContactService contactService;

    @Mock
    private PersonRepository personRepository;

    @Mock
    private ContactRepository contactRepository;

    String NAME = "Fake Name";

    String VALID_DOCUMENT = "76562599024";

    LocalDate BIRTHDATE = LocalDate.parse("1991-08-14");

    String CONTACT_NAME_ONE = "Fake Contact Name One";

    String CONTACT_NAME_TWO = "Fake Contact Name Two";

    String EMAIL_ONE = "fakeemailone@email.com";

    String EMAIL_TWO = "fakeemailtwo@email.com";

    ContactDTO contactDTO = ContactDTO.of(CONTACT_NAME_ONE, "44999876656", EMAIL_ONE);

    ContactDTO contactDTOTwo = ContactDTO.of(CONTACT_NAME_TWO, "44945236656", EMAIL_TWO);

    ContactCreateDTO contactCreateDTO = ContactCreateDTO.of(UUID.randomUUID(), CONTACT_NAME_TWO, "44945236656", EMAIL_TWO);

    ContactUpdateDTO contactUpdateDTO = ContactUpdateDTO.of(UUID.randomUUID(), CONTACT_NAME_TWO, null, EMAIL_TWO);

    Person person = Person.of(NAME, VALID_DOCUMENT, BIRTHDATE, List.of(contactDTO));

    @Test
    public void create_whenOk_shouldCreate() {
        when(personRepository.findById(any(UUID.class))).thenReturn(Optional.of(person));

        contactService.create(contactCreateDTO);

        verify(contactRepository).save(any(Contact.class));
    }

    @Test
    public void create_whenNotExistsPerson_shouldThrow() {
        when(personRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        Exception exception = assertThrows(PersonNotFoundByIdException.class, () -> {
            contactService.create(contactCreateDTO);
        });

        assertEquals(exception.getMessage(), "Person not found by ID");
    }

    @Test
    public void update_whenOk_shouldUpdate() {
        Contact contact = Contact.of(contactDTO, person);
        when(contactRepository.findById(any(UUID.class))).thenReturn(Optional.of(contact));

        Contact updatedContact = Contact.update(contact, contactUpdateDTO);

        when(contactRepository.save(updatedContact)).thenReturn(updatedContact);

        Contact response = contactService.update(contactUpdateDTO);

        verify(contactRepository).save(any(Contact.class));
        assertEquals(contactUpdateDTO.getName(), response.getName());
        assertEquals(contactUpdateDTO.getEmail(), response.getEmail());
        assertEquals(contact.getPhoneNumber(), response.getPhoneNumber());
    }

    @Test
    public void update_whenNotExistsContact_shouldThrow() {
        when(contactRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        Exception exception = assertThrows(ContactNotFoundByIdException.class, () -> {
            contactService.update(contactUpdateDTO);
        });

        assertEquals(exception.getMessage(), "Contact not found by ID");
    }

    @Test
    public void delete_whenOk_shouldDelete() {
        Person personWithMoreThanOneContact = Person.of(NAME, VALID_DOCUMENT, BIRTHDATE, List.of(contactDTO, contactDTOTwo));
        Contact contact = Contact.of(contactDTO, personWithMoreThanOneContact);
        when(contactRepository.findById(any(UUID.class))).thenReturn(Optional.of(contact));

        contactService.delete(UUID.randomUUID());

        verify(contactRepository).delete(any(Contact.class));
        verify(personRepository).save(any(Person.class));
    }

    @Test
    public void delete_whenHasOnlyOneContact_shouldThrow() {
        Contact contact = Contact.of(contactDTO, person);
        when(contactRepository.findById(any(UUID.class))).thenReturn(Optional.of(contact));

        Exception exception = assertThrows(ContactAtLeastOneException.class, () -> {
            contactService.delete(UUID.randomUUID());
        });

        assertEquals(exception.getMessage(), "A person should have at least one contact");
    }
}
