package com.freemanpivo.gremlinapp.repository;

import com.freemanpivo.gremlinapp.model.Person;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.T;
import org.springframework.stereotype.Repository;

import java.util.*;

import static org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.__.addV;
import static org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.__.unfold;

@Repository
@RequiredArgsConstructor
@Slf4j
public class PersonRepository implements IPersonRepository {
    private final GraphTraversalSource g;
    private static final String PERSON_LABEL = "PERSON";
    private static final String LIKES_LABEL = "LIKES";
    private static final String PERSON_ENTITY_ID = "entity_id";
    private static final String PERSON_NAME = "name";

    @Override
    public void save(Person person) {
        try {
            final var id = PERSON_LABEL.concat("#").concat(person.getEntityId());
            g.V().has(PERSON_LABEL, PERSON_ENTITY_ID, person.getEntityId()).fold().coalesce(
                    unfold(), addV(PERSON_LABEL).property(T.id, id)
                            .property(PERSON_NAME, person.getName())
                            .property(PERSON_ENTITY_ID, person.getEntityId())
            ).next();
        } catch (Exception e) {
            log.error("error creating a vertex...");
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Person> getById(String id) {
        try {
            final var vertex = g.V(id).elementMap().next();
            final var person = new Person(vertex.get(PERSON_ENTITY_ID).toString(), vertex.get(PERSON_NAME).toString());

            return Optional.of(person);
        } catch (NoSuchElementException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Person> getByEntityId(String entityId) {
        try {
            final var vertex = g.V().has(PERSON_LABEL, PERSON_ENTITY_ID, entityId).elementMap().next();
            final var person = new Person(vertex.get(PERSON_ENTITY_ID).toString(), vertex.get(PERSON_NAME).toString());

            return Optional.of(person);
        } catch (NoSuchElementException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Person> peopleWhoLikes(Person person) {
        try {
            List<Person> persons = new ArrayList<>();
            final var vertices = g.V().has(PERSON_LABEL, PERSON_ENTITY_ID, person.getEntityId())
                    .in(LIKES_LABEL).valueMap().toList();

            for (Map<Object, Object> vertex : vertices) {
                persons.add(
                        new Person(
                                vertex.get(PERSON_ENTITY_ID).toString(),
                                vertex.get(PERSON_NAME).toString()
                        )
                );
            }

            return persons;
        } catch (NoSuchElementException e) {
            log.info("message: {}", e.getMessage());
            return List.of();
        }
    }

    @Override
    public List<Person> peopleWhoLikes(String personId) {
        // TODO
        return null;
    }
}
