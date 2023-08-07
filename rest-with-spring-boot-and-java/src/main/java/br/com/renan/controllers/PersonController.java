package br.com.renan.controllers;

import br.com.renan.Services.PersonService;
import br.com.renan.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping(value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Person> findById(@PathVariable(value = "id") Long id) {
        try {
            return ResponseEntity.ok(personService.findById(id));
        } catch (Exception exception){
            return ResponseEntity.notFound().build();
        }

    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Person create(@RequestBody Person person) {

        return personService.create(person);

    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Person> update(@RequestBody Person person) {
        try{
            return ResponseEntity.ok(personService.update(person));
        } catch (Exception exception){
            return ResponseEntity.notFound().build();
        }

    }
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {

        personService.delete(id);

        return ResponseEntity.noContent().build();

    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Person> findAll() {

        return personService.findAll();

    }

}
