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
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ContactService {

    final ContactRepository contactRepository;

    final PersonRepository personRepository;

    public ContactService(ContactRepository contactRepository, PersonRepository personRepository) {
        this.contactRepository = contactRepository;
        this.personRepository = personRepository;
    }

    @Transactional
    public Contact create(ContactCreateDTO contactCreateDTO) {
        Person person = personRepository.findById(contactCreateDTO.getPersonId()).orElseThrow(PersonNotFoundByIdException::new);
        ContactDTO contactDTO = ContactDTO.of(contactCreateDTO);
        return contactRepository.save(Contact.of(contactDTO, person));
    }

    @Transactional
    public Contact update(ContactUpdateDTO contactUpdateDTO) {
        Contact contact = this.findById(contactUpdateDTO.getId());
        Contact updatedContact = Contact.update(contact, contactUpdateDTO);
        return contactRepository.save(updatedContact);
    }

    @Transactional
    public void delete(UUID id) {
        Contact contact = this.findById(id);
        Person person = contact.getPerson();
        if(person.getContacts().size() == 1) {
            throw new ContactAtLeastOneException();
        }
        person.getContacts().removeIf(it -> it.getId() == id);
        contactRepository.delete(contact);
        personRepository.save(person);
    }

    private Contact findById(UUID id) {
        return contactRepository.findById(id).orElseThrow(ContactNotFoundByIdException::new);
    }
}
