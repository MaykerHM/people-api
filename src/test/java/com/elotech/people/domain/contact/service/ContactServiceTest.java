package com.elotech.people.domain.contact.service;

import com.elotech.people.domain.contact.repository.ContactRepository;
import com.elotech.people.domain.person.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class ContactServiceTest {

    @InjectMocks
    private ContactService contactService;

    @Mock
    private PersonRepository personRepository;

    @Mock
    private ContactRepository contactRepository;

    @Test
    public void create_whenOk_shouldCreate() {

    }
}
