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

    ContactDTO contactDTO = ContactDTO.of("Fake Contact Name", "44999876656", "fakeemail@email.com");

    ContactDTO contactDTOTwo = ContactDTO.of("Fake Contact Name Two", "44945236656", "fakeemailtwo@email.com");

    ContactCreateDTO contactCreateDTO = ContactCreateDTO.of(UUID.randomUUID(), "Fake Contact Name Two", "44945236656", "fakeemailtwo@email.com");

    ContactUpdateDTO contactUpdateDTO = ContactUpdateDTO.of(UUID.randomUUID(), "Fake Contact Name Two", "44945236656", "fakeemailtwo@email.com");

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

        contactService.update(contactUpdateDTO);

        verify(contactRepository).save(any(Contact.class));
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
