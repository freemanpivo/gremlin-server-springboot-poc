package com.freemanpivo.gremlinapp.repository;

import com.freemanpivo.gremlinapp.model.Person;

import java.util.List;
import java.util.Optional;

public interface IPersonRepository {
    void save(Person person);
    Optional<Person> getById(String id);
    Optional<Person> getByEntityId(String entityId);
    List<Person> peopleWhoLikes(Person person);
    List<Person> peopleWhoLikes(String personId);
}
