package com.apishoppage.api.service;

import com.apishoppage.api.entity.Person;
import com.apishoppage.api.repository.PersonRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class PersonService {
    private final PersonRepository personRepository;

    public Person save(Person person) {
        return personRepository.save(person);
    }

    public List<Person> findAll() {
        return personRepository.findAll();
    }

    public Optional<Person> findById(UUID id){
        return personRepository.findById(id);
    }

    public Boolean deleteById(UUID id){
       if (personRepository.existsById(id)){
           personRepository.deleteById(id);
           return true;
       }
       return false;
    }

    public Optional<Person> updated(UUID id, Person person){
        return personRepository.findById(id)
                .map(existingPerson -> {
                    existingPerson.setName(person.getName());
                    existingPerson.setAge(person.getAge());
                    existingPerson.setCity(person.getCity());
                    existingPerson.setEmail(person.getEmail());
                    existingPerson.setPhone(person.getPhone());
                    return existingPerson;
                });
    }

    public Optional<Person> patch(UUID id, Map<String, Object> updates){
        return personRepository.findById(id)
                .map(existing -> {
                    if (updates.containsKey("name")) {
                        existing.setName((String) updates.get("name"));
                    }
                    if (updates.containsKey("age")){
                        existing.setAge((Integer)  updates.get("age"));
                    }
                    if (updates.containsKey("city")) {
                        existing.setCity((String) updates.get("city"));
                    }
                    if (updates.containsKey("email")) {
                        existing.setEmail((String) updates.get("email"));
                    }
                    if (updates.containsKey("phone")) {
                        existing.setPhone((String) updates.get("phone"));
                    }
                    return existing;
                });
    }


}
