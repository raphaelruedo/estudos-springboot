package com.example.demo.controller;

import com.example.demo.entity.Person;
import com.example.demo.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class PersonController {
    @Autowired
    private PersonRepository _personController;

    @RequestMapping(value = "v1/persons", method = RequestMethod.GET)
    public List<Person> Get() {
        return _personController.findAll();
    }

    @RequestMapping(value = "v1/persons/{id}", method = RequestMethod.GET)
    public ResponseEntity<Person> GetById(@PathVariable(value = "id") long id)
    {
        Optional<Person> person = _personController.findById(id);
        if(person.isPresent())
            return new ResponseEntity<Person>(person.get(), HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "v1/persons", method =  RequestMethod.POST)
    public Person Post(@Valid @RequestBody Person person)
    {
        return _personController.save(person);
    }

    @RequestMapping(value = "v1/persons/{id}", method =  RequestMethod.PUT)
    public ResponseEntity<Person> Put(@PathVariable(value = "id") long id, @Valid @RequestBody Person newPerson)
    {
        Optional<Person> oldPerson = _personController.findById(id);
        if(oldPerson.isPresent()){
            Person person = oldPerson.get();
            person.setName(newPerson.getName());
            _personController.save(person);
            return new ResponseEntity<Person>(person, HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "v1/persons/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> Delete(@PathVariable(value = "id") long id)
    {
        Optional<Person> person = _personController.findById(id);
        if(person.isPresent()){
            _personController.delete(person.get());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}