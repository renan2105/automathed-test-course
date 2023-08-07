package br.com.renan.Services;

import br.com.renan.exceptions.ResourceNotFoundException;
import br.com.renan.models.Person;
import br.com.renan.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class PersonService {

    @Autowired
    PersonRepository personRepository;

    private Logger logger = Logger.getLogger(PersonService.class.getName());

    public List<Person> findAll(){

        logger.info("Finding all people!");

        return personRepository.findAll();
    }

    public Person findById(Long id){

        logger.info("Finding one person!");

        return personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No Records found for this ID!"));
    }

    public Person create(Person person){

        logger.info("Creating one person!");

        return personRepository.save(person);
    }

    public Person update(Person person){

        logger.info("Update one person!");

        Person entity = personRepository.findById(person.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No Records found for this ID!"));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        return personRepository.save(entity);
    }

    public void delete(Long id){

        logger.info("Delete one person!");

        Person entity = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No Records found for this ID!"));

        personRepository.delete(entity);

    }
}
