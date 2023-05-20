package com.elotech.people.domain.person.service;

import com.elotech.people.domain.person.Person;
import com.elotech.people.domain.person.exception.PersonNotFoundByIdException;
import com.elotech.people.domain.person.repository.PersonRepository;
import com.elotech.people.domain.person.repository.PersonSearchCriteria;
import com.elotech.people.domain.person.repository.PersonSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Service
public class PersonService {

    final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Page<Person> findAll(String name, String document, LocalDate birthdateStart, LocalDate birthdateEnd, Pageable pageable) {
        PersonSearchCriteria criteria = new PersonSearchCriteria(name, document, birthdateStart, birthdateEnd);
        Specification<Person> spec = PersonSpecification.findByCriteria(criteria);
        return personRepository.findAll(spec, pageable);
    }

    public Person findById(UUID id) {
        return personRepository.findById(id).orElseThrow(PersonNotFoundByIdException::new);
    }
}
