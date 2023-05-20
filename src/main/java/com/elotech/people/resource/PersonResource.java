package com.elotech.people.resource;

import com.elotech.people.domain.person.Person;
import com.elotech.people.domain.person.service.PersonService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/person")
public class PersonResource {
    final PersonService personService;

    public PersonResource(PersonService personService) {
        this.personService = personService;
    }
    @GetMapping
    public ResponseEntity<Page<Person>> getAllPersons(
            @RequestParam(value="name",  required=false) String name,
            @RequestParam(value="document",  required=false) String document,
            @RequestParam(value="birthdateStart",  required=false) LocalDate birthdateStart,
            @RequestParam(value="birthdateEnd",  required=false) LocalDate birthdateEnd,
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(personService.findAll(name, document, birthdateStart, birthdateEnd, pageable));
    }
}
