package org.example.movieapi.repository.query;

import org.example.movieapi.entity.Movie;
import org.example.movieapi.repository.IMovieRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("testq")
public class IMovieRepositoryQueriesTest {

    @Autowired
    IMovieRepository movieRepository;

    @Test
    void testFindByIdOk() {
        int id = 35279;
        // SQL: select movie + director (eager mode)
        var movieOpt = movieRepository.findById(id);
        assertTrue(movieOpt.isPresent());
        var movie = movieOpt.get();
        System.out.println(movie);
        System.out.println("\t - director: " + movie.getDirector());
        System.out.println("\t - cast: ");
        // SQL: select for actors (lazy mode)
        movie.getActors().forEach(a ->
                System.out.println("\t\t * " + a));
    }

    @Test
    void testFindByIdKo() {
        int id = 1;
        var movieOpt = movieRepository.findById(id);
        assertTrue(movieOpt.isEmpty());
    }

    @Test
    void testFindAll() {
        movieRepository.findAll()
                .stream()
                .limit(10)
                .forEach(m -> System.out.println("\t- " +m));
        // SQL: N+1 for director in eager mode
    }

    @ParameterizedTest
    @MethodSource("org.example.movieapi.utils.Generators#movieSorts")
    void testFindAllSort(Sort sort) {
        movieRepository.findAll(sort)
                .stream()
                .limit(10)
                .forEach(m -> System.out.println("\t- " +m));
        // SQL: N+1 for director in eager mode
    }

    @Test
    void testFindAllPageable() {
        var pageable = Pageable.ofSize(10);
        for (int i=0; i<3; i++) {
            var page = movieRepository.findAll(pageable);
            // api page: foreach, stream
            for (var m: page) {
                System.out.println(m.getTitle());
            }
            pageable = page.nextPageable(); // TODO: test if has next
        }
    }

    @Test
    void testFindAllExample() {
        var movie = new Movie();
        var matcher = ExampleMatcher.matching()
                        .withIgnorePaths("year");
        movie.setTitle("The Man Who Knew Too Much");
        movieRepository.findAll(Example.of(movie, matcher))
                .forEach(m -> System.out.println("\t- " + m));
    }

    @Test
    void testFindByTitleIgnoringCase(){
        String title = "the man who knew too much";
        assertAll(movieRepository.findByTitleIgnoringCase(title)
                .map(m -> () -> assertTrue(
                        title.compareToIgnoreCase(m.getTitle()) == 0)));
    }

    @Test
    void testFindByDirectorNameEndingWithIgnoreCase() {
        String directorName = "Eastwood";
        movieRepository.findByDirectorNameEndingWithIgnoreCase(directorName, Sort.by("year"))
                .forEach(m -> System.out.println("\t- " + m ));
    }

    @Test
    void testFindByActorRangeYear(){
        String actorName = "Brad Pitt";
        short year1 = 2000;
        short year2 = 2010;
        movieRepository.findByActorRangeYear(actorName, year1, year2)
                .forEach(m -> System.out.println("\t- " + m ));
    }

    @Test
    void testGetStatByDirectorBorn(){
        int year = 1930;
        movieRepository.getStatByDirectorBorn(year)
                .forEach(t -> System.out.println("\t - "
                        + t.get("name", String.class)
                        + ": count = " + t.get("count_movie", Long.class)
                        + " ; first year = " + t.get("min_year", Short.class)
                        + " ; last year = " + t.get("max_year", Short.class)
                        + " ; total duration = " + t.get("total_duration", Long.class)
                ));
    }

    @Test
    void testGetStatByDirectorBorn2(){
        int year = 1930;
        movieRepository.getStatByDirectorBorn2(year)
                .forEach(stats -> System.out.println("\t - "
                        + stats.getName()
                        + ": count = " + stats.getMovieCount()
                        + " ; first year = " + stats.getMinYear()
                        + " ; last year = " + stats.getMaxYear()
                        + " ; total duration = " + stats.getTotalDuration()
                ));
    }

}
