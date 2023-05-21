package com.elotech.people.resource;

import com.elotech.people.domain.contact.dto.ContactDTO;
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
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = PersonResource.class)
public class PersonResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonService personService;

    String NAME = "Fake Name";

    String VALID_DOCUMENT = "76562599024";

    LocalDate BIRTHDATE = LocalDate.parse("1991-08-14");

    ContactDTO contactDTO = ContactDTO.of("Fake Contact Name", "44999876656", "fakeemail@email.com");

    Person person = Person.of(NAME, VALID_DOCUMENT, BIRTHDATE, List.of(contactDTO));

    @Test
    public void getAllPersons_whenOk_shouldReturn200() throws Exception {
        mockMvc.perform(get("/persons")
                .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    public void getById_whenOk_shouldReturn200() throws Exception {
        when(personService.findById(any(UUID.class))).thenReturn(person);
        mockMvc.perform(get("/persons/{id}", UUID.randomUUID())
                .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    public void createPerson_whenOk_shouldReturn201() throws Exception {
        when(personService.create(any(PersonDTO.class))).thenReturn(person);
        mockMvc.perform(post("/persons")
                .contentType("application/json")
                .content("{\n" +
                        "    \"name\": \"Paulo\",\n" +
                        "    \"document\": \"39678653044\",\n" +
                        "    \"birthdate\": \"2003-04-18\",\n" +
                        "    \"contacts\": [\n" +
                        "        {\n" +
                        "            \"name\": \"Mae\",\n" +
                        "            \"phoneNumber\": \"99999955656\",\n" +
                        "            \"email\": \"mae@email.com\"\n" +
                        "        }\n" +
                        "    ]\n" +
                        "}"))
                .andExpect(status().isCreated());
    }

    @Test
    public void updatePerson_whenOk_shouldReturn200() throws Exception {
        when(personService.create(any(PersonDTO.class))).thenReturn(person);
        mockMvc.perform(put("/persons")
                        .contentType("application/json")
                        .content("{\n" +
                                "    \"id\": \"808d9973-747b-40c3-ab62-4e8c9297da9c\",\n" +
                                "    \"name\": \"Paulo\",\n" +
                                "    \"document\": \"234.120.030-34\",\n" +
                                "    \"birthdate\": \"2000-10-23\"\n" +
                                "}"))
                .andExpect(status().isOk());
    }

    @Test
    public void deletePerson_whenOk_shouldReturn200() throws Exception {
        when(personService.findById(any(UUID.class))).thenReturn(person);
        mockMvc.perform(delete("/persons/{id}", UUID.randomUUID()))
                .andExpect(status().isOk());
    }
}
