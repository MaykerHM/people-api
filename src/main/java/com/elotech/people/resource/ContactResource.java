package com.elotech.people.resource;

import com.elotech.people.domain.contact.dto.ContactCreateDTO;
import com.elotech.people.domain.contact.dto.ContactUpdateDTO;
import com.elotech.people.domain.contact.exception.ContactAtLeastOneException;
import com.elotech.people.domain.contact.exception.ContactNotFoundByIdException;
import com.elotech.people.domain.contact.exception.InvalidEmailException;
import com.elotech.people.domain.contact.service.ContactService;
import com.elotech.people.domain.person.dto.PersonDTO;
import com.elotech.people.domain.person.dto.PersonFindByIdDTO;
import com.elotech.people.domain.person.dto.PersonUpdateDTO;
import com.elotech.people.domain.person.exception.InvalidBirthdateException;
import com.elotech.people.domain.person.exception.InvalidDocumentException;
import com.elotech.people.domain.person.exception.PersonAlreadyExistsWithDocumentException;
import com.elotech.people.domain.person.exception.PersonNotFoundByIdException;
import com.elotech.people.domain.person.service.PersonService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/contacts")
public class ContactResource {
    final ContactService contactService;

    public ContactResource(ContactService contactService) {
        this.contactService = contactService;
    }

    @PostMapping
    public ResponseEntity<Object> createContact(@RequestBody @Valid ContactCreateDTO contactCreateDTO) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(contactService.create(contactCreateDTO));
        } catch (PersonNotFoundByIdException | InvalidEmailException err) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err.getMessage());
        } catch (Exception err) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(err.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<Object> updateContact(@RequestBody @Valid ContactUpdateDTO contactUpdateDTO) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(contactService.update(contactUpdateDTO));
        } catch (ContactNotFoundByIdException err) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err.getMessage());
        } catch (Exception err) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(err.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteContact(@PathVariable(value = "id") UUID id) {
        try {
            contactService.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body("Contact deleted successfully!");
        } catch (ContactNotFoundByIdException | ContactAtLeastOneException err) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err.getMessage());
        } catch (Exception err) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(err.getMessage());
        }
    }
}
