package org.example.movieapi.repository;

import org.example.movieapi.entity.Person;
import org.example.movieapi.repository.IPersonRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PersonTest {
    @Autowired
    IPersonRepository personRepository;

    @Test
    void testPerson() {
        //var person = new Person(null, "Steve McQueen", LocalDate.of(1930, 3, 24));
        var person = Person.builder()
                .name("Steve McQueen")
                .birthdate(LocalDate.of(1930, 3, 24))
                .build();
        personRepository.saveAndFlush(person);
    }

}