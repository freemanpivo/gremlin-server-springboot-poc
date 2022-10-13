package com.freemanpivo.gremlinapp.graphdb.config;

import com.freemanpivo.gremlinapp.model.Person;
import com.freemanpivo.gremlinapp.repository.ILikeRepository;
import com.freemanpivo.gremlinapp.repository.IPersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class SampleGraphInsertion implements CommandLineRunner {
    private final IPersonRepository personRepository;
    private final ILikeRepository likeRepository;

    @Override
    public void run(String... args) throws Exception {
        personRepository.save(new Person("1234", "Pedro"));
        personRepository.save(new Person("123", "Joao"));
        personRepository.save(new Person("12", "Maria"));
        personRepository.save(new Person("1", "Alambrado"));

        likeRepository.save("1234", "1");
        likeRepository.save("1234", "12");
        likeRepository.save("1", "1234");
        likeRepository.save("12", "1234");

        final var person1 = personRepository.getById("PERSON#1234").get();
        log.info("FOUND PEDRO ==> {}", personRepository.getById("PERSON#1234").get());
        log.info("FOUND JOAO ==> {}", personRepository.getById("PERSON#123").get());
        log.info("FOUND MARIA ==> {}", personRepository.getById("PERSON#12").get());
        log.info("FOUND ALAMBRADO ==> {}", personRepository.getById("PERSON#1").get());

        final var pedroLikes = personRepository.peopleWhoLikes(person1);
        log.info("PEOPLE LIKES PEDRO >>> {}", pedroLikes);

        // TRICKY DATA => ELEMENT MAP vs VALUEMAP!
    }
}
