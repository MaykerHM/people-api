package com.elotech.people.domain.person.service;

import com.elotech.people.domain.contact.dto.ContactDTO;
import com.elotech.people.domain.person.Person;
import com.elotech.people.domain.person.dto.PersonDTO;
import com.elotech.people.domain.person.exception.PersonAlreadyExistsWithDocumentException;
import com.elotech.people.domain.person.exception.PersonNotFoundByIdException;
import com.elotech.people.domain.person.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(SpringExtension.class)
public class PersonServiceTest {

    @InjectMocks
    private PersonService personService;

    @Mock
    private PersonRepository personRepository;

    String NAME = "Fake Name";

    String VALID_DOCUMENT = "76562599024";

    LocalDate BIRTHDATE_START = LocalDate.parse("1990-01-01");

    LocalDate BIRTHDATE_END = LocalDate.parse("2010-12-31");

    LocalDate BIRTHDATE = LocalDate.parse("1991-08-14");

    ContactDTO contactDTO = ContactDTO.of("Fake Contact Name", "44999876656", "fakeemail@email.com");

    Person person = Person.of(NAME, VALID_DOCUMENT, BIRTHDATE, List.of(contactDTO));

    @Mock
    Pageable pageable;

    @Mock
    PersonDTO personDTO;

    @Test
    public void findAll_shouldFindAllWithSpecification() {
        Page<Person> response = new PageImpl<>(List.of(person));
        when(personRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(response);
        personService.findAll(NAME, VALID_DOCUMENT, BIRTHDATE_START, BIRTHDATE_END, pageable);

        verify(personRepository, times(1)).findAll(any(Specification.class), any(Pageable.class));
    }

    @Test
    public void findById_whenOk_shouldReturnPerson() {
        when(personRepository.findById(any(UUID.class))).thenReturn(Optional.of(person));
        personService.findById(UUID.randomUUID());

        verify(personRepository, times(1)).findById(any(UUID.class));
    }

    @Test
    public void findById_whenNotExistsPerson_shouldThrow() {
        when(personRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        Exception exception = assertThrows(PersonNotFoundByIdException.class, () -> {
            personService.findById(UUID.randomUUID());
        });

        assertEquals(exception.getMessage(), "Person not found by ID");
    }

    @Test
    public void save_whenOk_shouldSave() {
        when(personRepository.existsByDocument(anyString())).thenReturn(false);
        when(personDTO.getDocument()).thenReturn(VALID_DOCUMENT);
        when(personDTO.getBirthdate()).thenReturn(BIRTHDATE);
        personService.create(personDTO);

        verify(personRepository).save(any());
    }

    @Test
    public void save_whenAlreadyExistsByDocument_shouldThrow() {
        when(personRepository.existsByDocument(anyString())).thenReturn(true);
        when(personDTO.getDocument()).thenReturn(VALID_DOCUMENT);

        Exception exception = assertThrows(PersonAlreadyExistsWithDocumentException.class, () -> {
            personService.create(personDTO);
        });

        assertEquals("Person already exists with this document", exception.getMessage());
    }

}
