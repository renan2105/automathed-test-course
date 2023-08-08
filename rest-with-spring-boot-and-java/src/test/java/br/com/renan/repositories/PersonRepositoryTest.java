package br.com.renan.repositories;

import br.com.renan.integrationtests.testcontainers.AbstractIntegrationTest;
import br.com.renan.models.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PersonRepositoryTest extends AbstractIntegrationTest {

    @Autowired
    private PersonRepository personRepository;


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

    @DisplayName("JUnit test for Given Person Object when Save then Return Saved Person")
    @Test
    void testGivenPersonObject_whenSave_thenReturnSavedPerson() {

        // Given / Arrange

        // When / Act
        Person savedPerson = personRepository.save(person);

        // Then / Assert
        assertNotNull(savedPerson);
        assertTrue(savedPerson.getId() > 0);
    }

    @DisplayName("JUnit test for Given Person List when findAll then Return Person List")
    @Test
    void testGivenPersonList_whenFindAll_thenReturnPersonList() {

        // Given / Arrange
        Person person1 = new Person(
                "Leandro",
                "Costa",
                "leandro@erudio.com.br",
                "Uberlândia - Minas Gerais - Brasil",
                "Male");

        personRepository.save(person);
        personRepository.save(person1);

        // When / Act
        List<Person> personList = personRepository.findAll();

        // Then / Assert
        assertNotNull(personList);
        assertEquals(3, personList.size());
    }

    @DisplayName("JUnit test for Given Person Object when findById then Return Person Object")
    @Test
    void testGivenPersonObject_whenFindById_thenReturnPersonObject() {

        // Given / Arrange

        personRepository.save(person);
        // When / Act
        Person savedPerson = personRepository.findById(person.getId()).get();

        // Then / Assert
        assertNotNull(savedPerson);
        assertEquals(savedPerson.getId(), person.getId());
    }

    @DisplayName("JUnit test for Given Person Object when findByEmail then Return Person Object")
    @Test
    void testGivenPersonObject_whenFindByEmail_thenReturnPersonObject() {

        // Given / Arrange

        personRepository.save(person);
        // When / Act
        Person savedPerson = personRepository.findByEmail(person.getEmail()).get();

        // Then / Assert
        assertNotNull(savedPerson);
        assertEquals(savedPerson.getEmail(), person.getEmail());
    }

    @DisplayName("JUnit test for Given Person Object when updatePerson then Return Updated Person Object")
    @Test
    void testGivenPersonObject_whenUpdatePerson_thenReturnUpdatedPersonObject() {

        // Given / Arrange

        personRepository.save(person);
        // When / Act
        Person savedPerson = personRepository.findById(person.getId()).get();
        savedPerson.setFirstName("Leonardo");
        savedPerson.setEmail("leonardo@erudio.com.br");

        Person updatedPerson = personRepository.save(savedPerson);

        // Then / Assert
        assertNotNull(savedPerson);
        assertEquals("Leonardo", updatedPerson.getFirstName());
        assertEquals("leonardo@erudio.com.br", updatedPerson.getEmail());
    }

    @DisplayName("JUnit test for Given Person Object when delete then remove Person")
    @Test
    void testGivenPersonObject_whenDelete_thenRemovePerson() {

        // Given / Arrange

        personRepository.save(person);
        // When / Act
        personRepository.deleteById(person.getId());

        Optional<Person> personOptional = personRepository.findById(person.getId());
        // Then / Assert
        assertTrue(personOptional.isEmpty());
    }

    @DisplayName("JUnit test for Given Firstname And LastName when FindByJPQL then Return Person Object")
    @Test
    void testGivenFirstnameAndLastName_whenFindByJPQL_thenReturnPersonObject() {

        // Given / Arrange

        String firstName = "Leandro";
        String lastName = "Costa";

        personRepository.save(person);
        // When / Act
        Person savedPerson = personRepository.findByJPQL(firstName, lastName);

        // Then / Assert
        assertNotNull(savedPerson);
        assertEquals(firstName, person.getFirstName());
        assertEquals(lastName, person.getLastName());
    }

    @DisplayName("JUnit test for Given Firstname And LastName when FindByJPQLNamedParameters then Return Person Object")
    @Test
    void testGivenFirstnameAndLastName_whenFindByJPQLNamedParameters_thenReturnPersonObject() {

        // Given / Arrange

        String firstName = "Leandro";
        String lastName = "Costa";

        personRepository.save(person);
        // When / Act
        Person savedPerson = personRepository.findByJPQLNamedParameters(firstName, lastName);

        // Then / Assert
        assertNotNull(savedPerson);
        assertEquals(firstName, person.getFirstName());
        assertEquals(lastName, person.getLastName());
    }

    @DisplayName("JUnit test for Given Firstname And LastName when FindByNativeSQL then Return Person Object")
    @Test
    void testGivenFirstnameAndLastName_whenFindByNativeSQL_thenReturnPersonObject() {

        // Given / Arrange

        String firstName = "Leandro";
        String lastName = "Costa";

        personRepository.save(person);
        // When / Act
        Person savedPerson = personRepository.findByNativeSQL(firstName, lastName);

        // Then / Assert
        assertNotNull(savedPerson);
        assertEquals(firstName, person.getFirstName());
        assertEquals(lastName, person.getLastName());
    }

    @DisplayName("JUnit test for Given Firstname And LastName when FindByNativeSQLWithNamedParameters then Return Person Object")
    @Test
    void testGivenFirstnameAndLastName_whenFindByNativeSQLWithNamedParameters_thenReturnPersonObject() {

        // Given / Arrange

        String firstName = "Leandro";
        String lastName = "Costa";

        personRepository.save(person);
        // When / Act
        Person savedPerson = personRepository.findByNativeSQLWithNamedParameters(firstName, lastName);

        // Then / Assert
        assertNotNull(savedPerson);
        assertEquals(firstName, person.getFirstName());
        assertEquals(lastName, person.getLastName());
    }
}