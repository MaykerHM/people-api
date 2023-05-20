package com.elotech.people.domain.person.service;

import com.elotech.people.domain.contact.dto.ContactDTO;
import com.elotech.people.domain.person.Person;
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
import static org.mockito.Mockito.*;
import java.time.LocalDate;
import java.util.List;

@ExtendWith(SpringExtension.class)
public class PersonServiceTest {

    @InjectMocks
    private PersonService personService;

    @Mock
    private PersonRepository personRepository;

    String NAME = "Fake Name";

    String DOCUMENT = "76562599024";

    LocalDate BIRTHDATE_START = LocalDate.parse("1990-01-01");

    LocalDate BIRTHDATE_END = LocalDate.parse("2010-12-31");

    LocalDate BIRTHDATE = LocalDate.parse("1991-08-14");

    ContactDTO contactDTO = ContactDTO.of("Fake Contact Name", "44999876656", "fakeemail@email.com");

    Person person = Person.of(NAME, DOCUMENT, BIRTHDATE, List.of(contactDTO));

    @Mock
    Pageable pageable;

    @Test
    public void findAll_shouldFindAllWithSpecification() {
        Page<Person> response = new PageImpl<>(List.of(person));
        when(personRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(response);
        personService.findAll(NAME, DOCUMENT, BIRTHDATE_START, BIRTHDATE_END, pageable);

        verify(personRepository, times(1)).findAll(any(Specification.class), any(Pageable.class));
    }
}