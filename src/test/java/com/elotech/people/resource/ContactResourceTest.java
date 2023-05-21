package com.elotech.people.resource;

import com.elotech.people.domain.contact.Contact;
import com.elotech.people.domain.contact.dto.ContactCreateDTO;
import com.elotech.people.domain.contact.dto.ContactDTO;
import com.elotech.people.domain.contact.dto.ContactUpdateDTO;
import com.elotech.people.domain.contact.service.ContactService;
import com.elotech.people.domain.person.Person;
import com.elotech.people.domain.person.dto.PersonDTO;
import com.elotech.people.domain.person.service.PersonService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = ContactResource.class)
public class ContactResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ContactService contactService;

    String NAME = "Fake Name";

    String VALID_DOCUMENT = "76562599024";

    LocalDate BIRTHDATE = LocalDate.parse("1991-08-14");

    ContactDTO contactDTO = ContactDTO.of("Fake Contact Name One", "44999876656", "fakeemailone@email.com");

    Person person = Person.of(NAME, VALID_DOCUMENT, BIRTHDATE, List.of(contactDTO));

    Contact contact = Contact.of(contactDTO, person);

    @Test
    public void createContact_whenOk_shouldReturn201() throws Exception {
        when(contactService.create(any(ContactCreateDTO.class))).thenReturn(contact);
        mockMvc.perform(post("/contacts")
                .contentType("application/json")
                .content("{\n" +
                        "    \"personId\": \"808d9973-747b-40c3-ab62-4e8c9297da9c\",\n" +
                        "    \"name\": \"Fulano Teste\",\n" +
                        "    \"phoneNumber\": \"99999955656\",\n" +
                        "    \"email\": \"teste@email.com\"\n" +
                        "}"))
                .andExpect(status().isCreated());
    }

    @Test
    public void updateContact_whenOk_shouldReturn200() throws Exception {
        when(contactService.update(any(ContactUpdateDTO.class))).thenReturn(contact);
        mockMvc.perform(put("/contacts")
                        .contentType("application/json")
                        .content("{\n" +
                                "    \"id\": \"f7cd040f-159c-4603-a76f-405f48aadf58\",\n" +
                                "    \"name\": \"Contato Teste PUT contact Somente Nome\"\n" +
                                "}"))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteContact_whenOk_shouldReturn200() throws Exception {
        mockMvc.perform(delete("/contacts/{id}", UUID.randomUUID()))
                .andExpect(status().isOk());
    }
}
