package com.apishoppage.api.controller;

import com.apishoppage.api.entity.Person;

import com.apishoppage.api.service.PersonService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("api/person")
public class PersonController {
    private final PersonService service;

    @Autowired
    public PersonController(PersonService service){
        this.service = service;
    }

    @PostMapping
    public Person Post(@Valid @RequestBody Person person) {
        return service.save(person);
    }

    @GetMapping()
    public List<Person> GetAll(){
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> GetById(@PathVariable UUID id){
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> Delete(@PathVariable UUID id){
        boolean deleted = service.deleteById(id);
        if(deleted){
            return ResponseEntity.ok().build();
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Person> Update(@PathVariable UUID id, @Valid @RequestBody Person person){
        return service.updated(id, person)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Person> Patch(
            @PathVariable UUID id,
            @Valid @RequestBody Map<String, Object> updates){
        return service.patch(id,updates)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }









}
