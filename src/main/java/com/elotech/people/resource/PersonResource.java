package com.elotech.people.resource;

import com.elotech.people.domain.person.dto.PersonDTO;
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
@RequestMapping("/person")
public class PersonResource {
    final PersonService personService;

    public PersonResource(PersonService personService) {
        this.personService = personService;
    }
    @GetMapping
    public ResponseEntity<Object> getAllPersons(
            @RequestParam(value="name",  required=false) String name,
            @RequestParam(value="document",  required=false) String document,
            @RequestParam(value="birthdateStart",  required=false) LocalDate birthdateStart,
            @RequestParam(value="birthdateEnd",  required=false) LocalDate birthdateEnd,
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(personService.findAll(name, document, birthdateStart, birthdateEnd, pageable));
        } catch (Exception err) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(err.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable(value = "id") UUID id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(personService.findById(id));
        } catch (PersonNotFoundByIdException err) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err.getMessage());
        } catch (Exception err) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(err.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<Object> createPerson(@RequestBody @Valid PersonDTO personDTO) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(personService.create(personDTO));
        } catch (PersonAlreadyExistsWithDocumentException | InvalidBirthdateException | InvalidDocumentException err) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err.getMessage());
        } catch (Exception err) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(err.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updatePerson(@PathVariable(value = "id") UUID id, @RequestBody @Valid PersonUpdateDTO personDTO) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(personService.update(personDTO, id));
        } catch (InvalidBirthdateException | InvalidDocumentException | PersonNotFoundByIdException err) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err.getMessage());
        } catch (Exception err) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(err.getMessage());
        }
    }
}
