package br.com.renan.integrationtests.controllers;

import br.com.renan.config.TestConfigs;
import br.com.renan.integrationtests.testcontainers.AbstractIntegrationTest;
import br.com.renan.models.Person;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.DeserializationFeature;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class PersonControllerIntegrationTest extends AbstractIntegrationTest {

    private static RequestSpecification requestSpecification;
    private static ObjectMapper objectMapper;
    private static Person person;

    @BeforeAll
    public static void setup() {

        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        requestSpecification = new RequestSpecBuilder()
                .setBasePath("/person")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        person = new Person(
                "Leandro",
                "Costa",
                "leandro@erudio.com.br",
                "Uberlândia - Minas Gerais - Brasil",
                "Male");
    }

    @Test
    @Order(1)
    @DisplayName("integration Test Given Person Object When Create One Person Should Return A Person Object")
    void integrationTestGivenPersonObject_When_CreateOnePerson_ShouldReturnAPersonObject() throws IOException {

        var content = given()
                .spec(requestSpecification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .body(person)
                .when()
                    .post()
                .then()
                    .statusCode(200)
                        .extract()
                            .body()
                                .asString();

        Person createdPerson = objectMapper.readValue(content, Person.class);

        person = createdPerson;

        assertNotNull(createdPerson);
        assertNotNull(createdPerson.getId());
        assertNotNull(createdPerson.getFirstName());
        assertNotNull(createdPerson.getLastName());
        assertNotNull(createdPerson.getEmail());
        assertNotNull(createdPerson.getAddress());
        assertNotNull(createdPerson.getGender());

        assertTrue(createdPerson.getId() > 0);
        assertEquals("Leandro", createdPerson.getFirstName());
        assertEquals("Costa", createdPerson.getLastName());
        assertEquals("leandro@erudio.com.br", createdPerson.getEmail());
        assertEquals("Uberlândia - Minas Gerais - Brasil", createdPerson.getAddress());
        assertEquals("Male", createdPerson.getGender());
    }

    @Test
    @Order(2)
    @DisplayName("integration Test Given Person Object When Update One Person Should Return A Updated Person Object")
    void integrationTestGivenPersonObject_When_UpdateOnePerson_ShouldReturnAUpdatedPersonObject() throws IOException {

        person.setFirstName("Renan");
        person.setEmail("renan@email.com");

        var content = given()
                .spec(requestSpecification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .body(person)
                .when()
                    .put()
                .then()
                    .statusCode(200)
                        .extract()
                            .body()
                                .asString();

        Person updatedPerson = objectMapper.readValue(content, Person.class);

        person = updatedPerson;

        assertNotNull(updatedPerson);
        assertNotNull(updatedPerson.getId());
        assertNotNull(updatedPerson.getFirstName());
        assertNotNull(updatedPerson.getLastName());
        assertNotNull(updatedPerson.getEmail());
        assertNotNull(updatedPerson.getAddress());
        assertNotNull(updatedPerson.getGender());

        assertTrue(updatedPerson.getId() > 0);
        assertEquals("Renan", updatedPerson.getFirstName());
        assertEquals("Costa", updatedPerson.getLastName());
        assertEquals("renan@email.com", updatedPerson.getEmail());
        assertEquals("Uberlândia - Minas Gerais - Brasil", updatedPerson.getAddress());
        assertEquals("Male", updatedPerson.getGender());
    }

    @Test
    @Order(3)
    @DisplayName("integration Test Given Person Id When Find By Id Should Return A Person Object")
    void integrationTestGivenPersonId_When_FindById_ShouldReturnAPersonObject() throws IOException {

        var content = given()
                .spec(requestSpecification)
                .pathParam("id", person.getId())
                .when()
                    .get("{id}")
                .then()
                    .statusCode(200)
                        .extract()
                            .body()
                                .asString();

        Person foundPerson = objectMapper.readValue(content, Person.class);

        assertNotNull(foundPerson);
        assertNotNull(foundPerson.getId());
        assertNotNull(foundPerson.getFirstName());
        assertNotNull(foundPerson.getLastName());
        assertNotNull(foundPerson.getEmail());
        assertNotNull(foundPerson.getAddress());
        assertNotNull(foundPerson.getGender());

        assertTrue(foundPerson.getId() > 0);
        assertEquals("Renan", foundPerson.getFirstName());
        assertEquals("Costa", foundPerson.getLastName());
        assertEquals("renan@email.com", foundPerson.getEmail());
        assertEquals("Uberlândia - Minas Gerais - Brasil", foundPerson.getAddress());
        assertEquals("Male", foundPerson.getGender());
    }

    @Test
    @Order(4)
    @DisplayName("integration Test When Find All Should Return A Person List")
    void integrationTest_When_FindAll_ShouldReturnAPersonList() throws IOException {

        Person anotherPerson = new Person(
                "Ruan",
                "Oliveira",
                "ruan@email.com.br",
                "Jandira - São Paulo - Brasil",
                "Male");

        given()
                .spec(requestSpecification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .body(anotherPerson)
                .when()
                    .post()
                .then()
                    .statusCode(200);

        var content = given()
                .spec(requestSpecification)
                .when()
                    .get()
                .then()
                    .statusCode(200)
                        .extract()
                            .body()
                                .asString();

        List<Person> personList = Arrays.asList(objectMapper.readValue(content, Person[].class));

        Person foundFirstPerson = personList.get(0);

        assertNotNull(foundFirstPerson);
        assertNotNull(foundFirstPerson.getId());
        assertNotNull(foundFirstPerson.getFirstName());
        assertNotNull(foundFirstPerson.getLastName());
        assertNotNull(foundFirstPerson.getEmail());
        assertNotNull(foundFirstPerson.getAddress());
        assertNotNull(foundFirstPerson.getGender());

        assertTrue(foundFirstPerson.getId() > 0);
        assertEquals("Renan", foundFirstPerson.getFirstName());
        assertEquals("Costa", foundFirstPerson.getLastName());
        assertEquals("renan@email.com", foundFirstPerson.getEmail());
        assertEquals("Uberlândia - Minas Gerais - Brasil", foundFirstPerson.getAddress());
        assertEquals("Male", foundFirstPerson.getGender());


        Person foundSecondPerson = personList.get(1);

        assertNotNull(foundSecondPerson);
        assertNotNull(foundSecondPerson.getId());
        assertNotNull(foundSecondPerson.getFirstName());
        assertNotNull(foundSecondPerson.getLastName());
        assertNotNull(foundSecondPerson.getEmail());
        assertNotNull(foundSecondPerson.getAddress());
        assertNotNull(foundSecondPerson.getGender());

        assertTrue(foundSecondPerson.getId() > 0);
        assertEquals("Ruan", foundSecondPerson.getFirstName());
        assertEquals("Oliveira", foundSecondPerson.getLastName());
        assertEquals("ruan@email.com.br", foundSecondPerson.getEmail());
        assertEquals("Jandira - São Paulo - Brasil", foundSecondPerson.getAddress());
        assertEquals("Male", foundSecondPerson.getGender());
    }

    @Test
    @Order(5)
    @DisplayName("integration Test Given Person Id When Delete Should Return No Content")
    void integrationTestGivenPersonId_When_Delete_ShouldReturnNoContent(){

        given()
                .spec(requestSpecification)
                .pathParam("id", person.getId())
            .when()
                .delete("{id}")
            .then()
                .statusCode(204);


    }
}