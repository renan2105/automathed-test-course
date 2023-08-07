package br.com.renan.controllers;

import br.com.renan.Services.PersonService;
import br.com.renan.exceptions.ResourceNotFoundException;
import br.com.renan.models.Person;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PersonService personService;

    private Person person;

    @BeforeEach
    public void setup() {
        person = new Person(
                "Leandro",
                "Costa",
                "leandro@erudio.com.br",
                "Uberlândia - Minas Gerais - Brasil",
                "Male");
    }

    @DisplayName("test Given Person Object When Create Person then Return Saved Person")
    @Test
    void testGivenPersonObject_WhenCreatePerson_thenReturnSavedPerson() throws Exception {

        //Given / Arrange
        given(personService.create(any(Person.class)))
                .willAnswer((invocation) -> invocation.getArgument(0));

        //When / Act
        ResultActions response = mockMvc.perform(post("/person")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(person)));

        //Then / Assert
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(person.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(person.getLastName())))
                .andExpect(jsonPath("$.email", is(person.getEmail())));

    }

    @DisplayName("test Given List Of Persons When FindAll Persons then Return Persons List")
    @Test
    void testGivenListOfPersons_WhenFindAllPersons_thenReturnPersonsList() throws Exception {

        //Given / Arrange
        List<Person> personList = new ArrayList<>();
        personList.add(person);
        personList.add(new Person(
                "Leonardo",
                "Costa",
                "leonardo@erudio.com.br",
                "Uberlândia - Minas Gerais - Brasil",
                "Male"));

        given(personService.findAll()).willReturn(personList);

        //When / Act
        ResultActions response = mockMvc.perform(get("/person"));

        //Then / Assert
        response
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()", is(personList.size())));
    }

    @DisplayName("test Given PersonId When Find By Id then Return Person Object")
    @Test
    void testGivenPersonId_WhenFindById_thenReturnPersonObject() throws Exception {

        //Given / Arrange
        long personId = 1L;
        given(personService.findById(personId)).willReturn(person);

        //When / Act
        ResultActions response = mockMvc.perform(get("/person/{id}", personId));

        //Then / Assert
        response
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.firstName", is(person.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(person.getLastName())))
                .andExpect(jsonPath("$.email", is(person.getEmail())));

    }

    @DisplayName("test Given Invalid PersonId When Find By Id then Return Not Found")
    @Test
    void testGivenInvalidPersonId_WhenFindById_thenReturnNotFound() throws Exception {

        //Given / Arrange
        long personId = 1L;
        given(personService.findById(personId)).willThrow(ResourceNotFoundException.class);

        //When / Act
        ResultActions response = mockMvc.perform(get("/person/{id}", personId));

        //Then / Assert
        response
                .andExpect(status().isNotFound())
                .andDo(print());

    }

    @DisplayName("test Given Update Person When Update then Return Updated Person Object")
    @Test
    void testGivenUpdatePerson_WhenUpdate_thenReturnUpdatedPersonObject() throws Exception {

        //Given / Arrange
        long personId = 1L;
        given(personService.findById(personId)).willReturn(person);
        given(personService.update(any(Person.class)))
                .willAnswer((invocation) -> invocation.getArgument(0));

        //When / Act
        Person updatedPerson = new Person(
                "Leonardo",
                "Costa",
                "leonardo@erudio.com.br",
                "Uberlândia - Minas Gerais - Brasil",
                "Male");

        ResultActions response = mockMvc.perform(put("/person")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(updatedPerson)));

        //Then / Assert
        response
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.firstName", is(updatedPerson.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(updatedPerson.getLastName())))
                .andExpect(jsonPath("$.email", is(updatedPerson.getEmail())));

    }

    @DisplayName("test Given Nonexistent Person When Update then Return NotFound")
    @Test
    void testGivenNonexistentPerson_WhenUpdate_thenReturnNotFound() throws Exception {

        //Given / Arrange
        long personId = 1L;
        given(personService.findById(personId)).willThrow(ResourceNotFoundException.class);
        given(personService.update(any(Person.class)))
                .willAnswer((invocation) -> invocation.getArgument(1));

        //When / Act
        Person updatedPerson = new Person(
                "Leonardo",
                "Costa",
                "leonardo@erudio.com.br",
                "Uberlândia - Minas Gerais - Brasil",
                "Male");

        ResultActions response = mockMvc.perform(put("/person")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(updatedPerson)));

        //Then / Assert
        response
                .andExpect(status().isNotFound())
                .andDo(print());

    }

    @DisplayName("test Given Person Id When Delete then Return Not Content")
    @Test
    void testGivenPersonId_WhenDelete_thenReturnNotContent() throws Exception {

        //Given / Arrange
        long personId = 1L;
        willDoNothing().given(personService).delete(personId);

        //When / Act
        ResultActions response = mockMvc.perform(delete("/person/{id}", personId));

        //Then / Assert
        response
                .andExpect(status().isNoContent())
                .andDo(print());

    }
}