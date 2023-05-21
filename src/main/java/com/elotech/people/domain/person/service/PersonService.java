package com.elotech.people.domain.person.service;

import com.elotech.people.domain.contact.repository.ContactRepository;
import com.elotech.people.domain.person.Person;
import com.elotech.people.domain.person.dto.PersonDTO;
import com.elotech.people.domain.person.dto.PersonUpdateDTO;
import com.elotech.people.domain.person.exception.PersonAlreadyExistsWithDocumentException;
import com.elotech.people.domain.person.exception.PersonNotFoundByIdException;
import com.elotech.people.domain.person.repository.PersonRepository;
import com.elotech.people.domain.person.repository.PersonSearchCriteria;
import com.elotech.people.domain.person.repository.PersonSpecification;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class PersonService {

    final PersonRepository personRepository;

    final ContactRepository contactRepository;

    public PersonService(PersonRepository personRepository, ContactRepository contactRepository) {
        this.personRepository = personRepository;
        this.contactRepository = contactRepository;
    }

    public Page<Person> findAll(String name, String document, LocalDate birthdateStart, LocalDate birthdateEnd, Pageable pageable) {
        PersonSearchCriteria criteria = new PersonSearchCriteria(name, document, birthdateStart, birthdateEnd);
        Specification<Person> spec = PersonSpecification.findByCriteria(criteria);
        return personRepository.findAll(spec, pageable);
    }

    public Person findById(UUID id) {
        return personRepository.findById(id).orElseThrow(PersonNotFoundByIdException::new);
    }

    @Transactional
    public Person create(PersonDTO personDto) {
        Boolean alreadyExistsByDocument = personRepository.existsByDocument(personDto.getDocument().replaceAll("\\D", ""));
        if (alreadyExistsByDocument) {
            throw new PersonAlreadyExistsWithDocumentException();
        }
        return personRepository.save(Person.of(personDto.getName(), personDto.getDocument(), personDto.getBirthdate(), personDto.getContacts()));
    }

    @Transactional
    public Person update(PersonUpdateDTO personDto, UUID id) {
        Person person = this.findById(id);
        if(PersonUpdateDTO.hasContacts(personDto)) {
            contactRepository.deleteAll(person.getContacts());
        }
        Person updatedPerson = Person.update(person, personDto);

        return personRepository.save(updatedPerson);
    }

    @Transactional
    public void delete(UUID id) {
        Person person = this.findById(id);
        personRepository.delete(person);
    }
}
