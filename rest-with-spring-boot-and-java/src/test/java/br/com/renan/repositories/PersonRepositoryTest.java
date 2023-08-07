package br.com.renan.repositories;

import br.com.renan.models.Person;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class PersonRepositoryTest {

    @Autowired
    private PersonRepository personRepository;

    private Person person0;

//    @DisplayName("Given person object when save then return saved person")
//    @Test
//    void testGivenPersonObject_whenSave_thenReturnSavedPerson(){
//        //Given / Arrange
//        Person person = new Person("Andre",
//                "Costa",
//                "Osasco - São Paulo - Brasil",
//                "Male",
//                "andre@email.com");
//
//        //When / Act
//        Person savedPerson = personRepository.save(person);
//        //Then / Assert
//        assertNotNull(savedPerson);
//        assertTrue(savedPerson.getId() > 0);
//
//    }

    @DisplayName("JUnit test for Given Person Object when Save then Return Saved Person")
    @Test
    void testGivenPersonObject_whenSave_thenReturnSavedPerson() {

        // Given / Arrange
        person0 = new Person(
                "Leandro",
                "Costa",
                "leandro@erudio.com.br",
                "Uberlândia - Minas Gerais - Brasil",
                "Male");

        // When / Act
        Person savedPerson = personRepository.save(person0);

        // Then / Assert
        assertNotNull(savedPerson);
        assertTrue(savedPerson.getId() > 0);
    }
}