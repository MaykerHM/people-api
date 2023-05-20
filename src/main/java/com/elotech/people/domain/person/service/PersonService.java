package com.elotech.people.domain.person.service;

import com.elotech.people.domain.person.Person;
import com.elotech.people.domain.person.repository.PersonRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Page<Person> findAll(Pageable pageable) {
        return personRepository.findAll(pageable);
    }
}
