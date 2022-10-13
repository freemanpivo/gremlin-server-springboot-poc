package com.freemanpivo.gremlinapp.graphdb.config;

import com.freemanpivo.gremlinapp.model.Person;
import com.freemanpivo.gremlinapp.repository.ILikeRepository;
import com.freemanpivo.gremlinapp.repository.IPersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class SampleGraphInsertion implements CommandLineRunner {
    private final IPersonRepository peopleRepository;
    private final ILikeRepository likesRepository;

    @Override
    public void run(String... args) throws Exception {
        peopleRepository.save(new Person("1234", "Pedro"));
        peopleRepository.save(new Person("123", "Joao"));
        peopleRepository.save(new Person("12", "Maria"));
        peopleRepository.save(new Person("1", "Alambrado"));

        likesRepository.save("1234", "1");
        likesRepository.save("1234", "12");
        likesRepository.save("1", "1234");
        likesRepository.save("12", "1234");

        final var pedro = peopleRepository.getById("PERSON#1234").get();
        log.info("FOUND PEDRO            ==> {}", peopleRepository.getById("PERSON#1234").get());
        log.info("FOUND JOAO             ==> {}", peopleRepository.getById("PERSON#123").get());
        log.info("FOUND MARIA            ==> {}", peopleRepository.getById("PERSON#12").get());
        log.info("FOUND ALAMBRADO        ==> {}", peopleRepository.getById("PERSON#1").get());

        final var pedroLikes = peopleRepository.peopleWhoLikes(pedro);
        log.info("PEOPLE WHO LIKES PEDRO ==> {}", pedroLikes);

        // TRICKY DATA => ELEMENT MAP vs VALUEMAP!
    }
}
