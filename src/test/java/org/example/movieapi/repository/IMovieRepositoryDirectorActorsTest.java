package org.example.movieapi.repository;

import org.example.movieapi.entity.Movie;
import org.example.movieapi.entity.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("testu")
public class IMovieRepositoryDirectorActorsTest {

    @Autowired
    IMovieRepository movieRepository;

    @Autowired
    IPersonRepository personRepository;

    @Autowired
    TestEntityManager entityManager;

    // @Rollback(false)
    @Test
    void testSetDirector() {
        var movie = new Movie("Top Gun: Maverick", (short) 2022);
        var person = new Person("Joseph Kosinski");
        Stream.of(movie, person)
                .forEach(entityManager::persist);
        entityManager.flush(); // SQL: insert x 2
        entityManager.clear();

        var movieReadOpt = movieRepository.findById(movie.getId());
        var personReadOpt = personRepository.findById(person.getId());

        assertTrue(movieReadOpt.isPresent());
        assertTrue(personReadOpt.isPresent());

        var movieRead = movieReadOpt.get();
        var personRead = personReadOpt.get();

        movieRead.setDirector(personRead);
        movieRepository.flush(); // SQL: update

        // check in DB
        entityManager.clear();
        var movieReadAgain = entityManager.find(Movie.class, movie.getId());
        // NB: with fetch eager, retrieve movie + director
        assertNotNull(movieReadAgain);
        var director = movieReadAgain.getDirector();
        // NB: with fetch lazy, retrieve director with extra query
        assertNotNull(director);
        assertEquals(person.getId(), director.getId());
        assertEquals(person.getName(), director.getName());
    }

    @Rollback(false)
    @Test
    void testActors() {
        var movie = new Movie("Top Gun: Maverick", (short) 2022);
        var tom = new Person("Tom Cruise");
        var jen = new Person("Jennifer Connelly");
        var val = new Person("Val Kilmer");
        Stream.of(movie, tom, jen, val)
                .forEach(entityManager::persist);
        entityManager.flush();
        entityManager.clear();

        var movieRead = movieRepository.findById(movie.getId()).get();
        var tomRead = personRepository.findById(tom.getId()).get();
        var jenRead = personRepository.findById(jen.getId()).get();

        movieRead.getActors().addAll(List.of(tomRead, jenRead));

        movieRepository.flush();
        entityManager.clear();

        var movieReadAgain = movieRepository.findById(movie.getId()).get();
        var valRead = personRepository.findById(val.getId()).get();

        movieReadAgain.getActors().add(val);
        movieRepository.flush();

    }








}
