package org.example.movieapi.repository;

import net.bytebuddy.utility.RandomString;
import org.example.movieapi.entity.Movie;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class IMovieRepositoryTest {

    @Autowired
    IMovieRepository movieRepository;

    @Autowired
    // EntityManager entityManager;
    TestEntityManager entityManager;

    @Test
    void testSave() {
        String title = "Top Gun: Maverick";
        short year = 2022;
        var movie = new Movie(title, year);
        // movieRepository.save(movie); // synchro here or later (cf strategy id)
        movieRepository.saveAndFlush(movie); // insert here

        // 1st check: id has been generated
        assertNotNull(movie.getId(), "id");

        // 2nd check if object is persisted
        entityManager.clear();
        // movie is detached from hibernate cache

        var movieRead = entityManager.find(Movie.class, movie.getId());
        assertNotNull(movieRead, "movie read from db");
        assertAll(
                () -> assertEquals(title, movieRead.getTitle(), "title"),
                () -> assertEquals(year, movieRead.getYear(), "year"));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "Z",
            "It",
            "Top Gun: Maverick",
            "Night of the Day of the Dawn of the Son of the Bride of the Return of the Revenge of the Terror of the Attack of the Evil Mutant Hellbound Flesh Eating Crawling Alien Zombified Subhumanoid Living Dead, Part 5"
    })
    void testSaveTitleOk(String title){
        var movie = new Movie(title, (short) 2011);
        assertDoesNotThrow(
                () -> movieRepository.saveAndFlush(movie));

    }

    @ParameterizedTest
    // @NullSource
    @NullAndEmptySource  // null ok everywhere, empty depends on db editor
    @MethodSource("org.example.movieapi.utils.Generators#titleTooLong")
    void testSaveTitleKo(String title){
        String longText = RandomString.make(301);
        var movie = new Movie(title, (short) 2011);
        assertThrows(DataIntegrityViolationException.class,
                () -> movieRepository.saveAndFlush(movie));
    }


    @Test
    void testUpdateAndDelete() {
        // hypothesis in dB
        String title = "Top Gun: Maverick";
        short year = 2022;
        var movie = new Movie(title, year);
        // SQL: insert
        entityManager.persistAndFlush(movie);
        entityManager.clear();

        // read data from db => cache hibernate
        // SQL: select
        var movieReadOpt = movieRepository.findById(movie.getId());
        assertTrue(movieReadOpt.isPresent(), "movie present by id");

        movieReadOpt.ifPresent(movieRead -> {
                movieRead.setTitle("Top Gun");
                movieRead.setYear((short) 1986);
                movieRepository.flush(); // SQL: update all row
                // TODO: clear cache and check if update ok in DB by reading again movie

                movieRepository.delete(movieRead);
                movieRepository.flush(); // SQL: delete
                // TODO: clear cache and check if delete ok in DB by reading again movie
        });
    }







}