package br.com.renan.Services;

import br.com.renan.exceptions.ResourceNotFoundException;
import br.com.renan.models.Person;
import br.com.renan.repositories.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
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

    @DisplayName("test Given Person Object when Create Person then Return Person Object")
    @Test
    void testGivenPersonObject_whenCreatePerson_thenReturnPersonObject() {

        //Given / Arrange
        given(personRepository.findByEmail(anyString())).willReturn(Optional.empty());
        given(personRepository.save(person)).willReturn(person);

        //When / Act
        Person savedPerson = personService.create(person);

        // Then / Assert
        assertNotNull(savedPerson);
        assertEquals("Leandro", savedPerson.getFirstName());

    }

    @DisplayName("test Given Existing Email when Create Person then Return Person Object")
    @Test
    void testGivenExistingEmail_whenCreatePerson_thenThrowsException() {

        //Given / Arrange
        given(personRepository.findByEmail(anyString())).willReturn(Optional.of(person));

        //When / Act
        assertThrows(ResourceNotFoundException.class, () -> {
            personService.create(person);
        });
        // Then / Assert
        verify(personRepository, never()).save(any(Person.class));

    }

    @DisplayName("test Given Persons List When FindAll Persons Then Return Persons List")
    @Test
    void testGivenPersonsList_WhenFindAllPersons_thenReturnPersonsList() {

        //Given / Arrange
        Person person1 = new Person(
                "Leandro",
                "Costa",
                "leandro@erudio.com.br",
                "Uberlândia - Minas Gerais - Brasil",
                "Male");

        given(personRepository.findAll()).willReturn(List.of(person, person1));

        //When / Act
        List<Person> personList = personService.findAll();

        // Then / Assert
        assertNotNull(personList);
        assertEquals(2, personList.size());

    }

    @DisplayName("test Given Empty Persons List When FindAll Persons Then Return Empty Persons List")
    @Test
    void testGivenEmptyPersonsList_WhenFindAllPersons_thenReturnEmptyPersonsList() {

        //Given / Arrange
        given(personRepository.findAll()).willReturn(Collections.emptyList());

        //When / Act
        List<Person> personList = personService.findAll();

        // Then / Assert
        assertTrue(personList.isEmpty());
        assertEquals(0, personList.size());

    }

    @DisplayName("test Given Person Id when FindById then Return Person Object")
    @Test
    void testGivenPersonId_whenFindById_thenReturnPersonObject() {

        //Given / Arrange
        given(personRepository.findById(anyLong())).willReturn(Optional.of(person));

        //When / Act
        Person savedPerson = personService.findById(1L);

        // Then / Assert
        assertNotNull(savedPerson);
        assertEquals("Leandro", savedPerson.getFirstName());

    }

    @DisplayName("test Given Person Object when Update Person then Return Updated Person Object")
    @Test
    void testGivenPersonObject_whenUpdatePerson_thenReturnUpdatedPersonObject() {

        //Given / Arrange
        person.setId(1L);
        given(personRepository.findById(anyLong())).willReturn(Optional.of(person));

        person.setFirstName("Leonardo");
        person.setEmail("leonardo@erudio.com.br");

        given(personRepository.save(person)).willReturn(person);

        //When / Act
        Person updatedPerson = personService.update(person);

        // Then / Assert
        assertNotNull(updatedPerson);
        assertEquals("Leonardo", updatedPerson.getFirstName());
        assertEquals("leonardo@erudio.com.br", updatedPerson.getEmail());

    }

    @DisplayName("test Given Person Id when Delete Person then Do Nothing")
    @Test
    void testGivenPersonId_whenDeletePerson_thenDoNothing() {

        //Given / Arrange
        person.setId(1L);
        given(personRepository.findById(anyLong())).willReturn(Optional.of(person));
        willDoNothing().given(personRepository).delete(person);

        //When / Act
        personService.delete(person.getId());

        // Then / Assert
        verify(personRepository, times(1)).delete(person);
    }

}