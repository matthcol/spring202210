package org.example.movieapi.repository.query;

import org.example.movieapi.entity.Person;
import org.example.movieapi.repository.IPersonRepository;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.test.context.ActiveProfiles;

import java.util.stream.Stream;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("testq")
public class IPersonRepositoryQueriesTest {

    @Autowired
    IPersonRepository personRepository;

    static Stream<Example> personExamples() {
        var person = new Person("Clint Eastwood");
        var personPartName = new Person("pitt");
        return Stream.of(
                Example.of(person),
                Example.of(personPartName,
                        ExampleMatcher.matching()
                                .withIgnoreCase("name")
                                .withStringMatcher(ExampleMatcher.StringMatcher.ENDING)
                )
        );
    }

    @ParameterizedTest
    @MethodSource("org.example.movieapi.repository.query.IPersonRepositoryQueriesTest#personExamples")
    void testFindAllByExample(Example example) {
        personRepository.findAll(example)
                .forEach(System.out::println);
    }
}
