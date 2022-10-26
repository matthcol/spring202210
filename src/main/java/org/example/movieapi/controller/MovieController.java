package org.example.movieapi.controller;

import org.example.movieapi.entity.Movie;
import org.example.movieapi.repository.IMovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Transactional
@RestController
@RequestMapping("/api/movies")
public class MovieController {

    @Autowired
    IMovieRepository movieRepository;

    @GetMapping
    public List<Movie> getAll() {
//        return Stream.of(
//                new Movie("Top Gun", (short) 1986),
//                new Movie("Top Gun: Maverick", (short) 2022)
//        );
        return movieRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> getById(@PathVariable int id) {
//        var movie = new Movie("Top Gun: Maverick", (short) 2022);
//        movie.setId(id);
//        return movie;
        var movieOpt = movieRepository.findById(id);
        return movieOpt.map(ResponseEntity::ok)
                 .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/byTitle")
    public Set<Movie> getByTitle(@RequestParam("t") String title){
        return  movieRepository.findByTitleIgnoringCase(title)
               // .collect(Collectors.toCollection(HashSet::new));
                .collect(Collectors.toSet());
    }

    // @RequestParam can tuned with name, required and defaultValue
    @GetMapping("/search")
    public List<Movie> getByTitleOrYear(
            @RequestParam(name = "t", required = false) String title,
            @RequestParam(name = "y", required = false) Short year1,
            @RequestParam(name = "y2", required = false) Short year2)
    {
        if (Objects.nonNull(title)
                && Objects.isNull(year1)
                && Objects.isNull(year2)
        ) {
            // find by title only
            return movieRepository.findByTitleIgnoringCase(title).toList();
        } else if (Objects.nonNull(year1)
                && Objects.isNull(year2)
        ) {
            // find by title year (title can be null)
            var movieExampleTitleYear = new Movie(title, year1);
            return movieRepository.findAll(Example.of(movieExampleTitleYear,
                    ExampleMatcher.matching().withIgnoreCase("title")));

        } else if (Objects.isNull(title)
                && Objects.nonNull(year1)
                && Objects.nonNull(year2)
        ) {
            // find by range year
            return movieRepository.findByYearBetween(year1, year2);
        } else if (Objects.nonNull(title)
                && Objects.nonNull(year1)
                && Objects.nonNull(year1)
        ) {
            // find by title and range year
            return movieRepository.findByTitleIgnoringCaseAndYearBetween(title, year1, year2);
        } else {
            // no criteria : too wide => error or empty list
            return List.of();
        }
    }

    @GetMapping("/byActor")
    public List<Movie> getByActor(@RequestParam("n") String actorName) {
        return movieRepository.findByActorsNameEndingWithIgnoreCase(actorName);
    }

    // @Valid not mandatory if spring-boot-starter-validation by default
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Movie addMovie(@RequestBody @Valid Movie movie){
        return movieRepository.save(movie);
    }


}
