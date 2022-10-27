package org.example.movieapi.controller;

import org.example.movieapi.dto.MovieDetailDto;
import org.example.movieapi.dto.MovieDto;
import org.example.movieapi.service.IMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;


@RestController
@RequestMapping("/api/movies")
public class MovieController {

    @Autowired
    IMovieService movieService;

    @GetMapping
    public List<MovieDto> getAll() {
//        return Stream.of(
//                new Movie("Top Gun", (short) 1986),
//                new Movie("Top Gun: Maverick", (short) 2022)
//        );
        return movieService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieDetailDto> getById(@PathVariable int id) {
//        var movie = new MovieDetailDto();
//        movie.setTitle("Top Gun: Maverick");
//        movie.setYear((short) 2022);
//        movie.setId(1);
//        return ResponseEntity.ok(movie);
        return movieService.getById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/byTitle")
    public Set<MovieDto> getByTitle(@RequestParam("t") String title){
        return  movieService.getByTitle(title);
    }

    // @RequestParam can tuned with name, required and defaultValue
    @GetMapping("/search")
    public Set<MovieDto> getByTitleOrYear(
            @RequestParam(name = "t", required = false) String title,
            @RequestParam(name = "y", required = false) Short year1,
            @RequestParam(name = "y2", required = false) Short year2)
    {
        if (Objects.nonNull(title)
                && Objects.isNull(year1)
                && Objects.isNull(year2)
        ) {
            // find by title only
            return movieService.getByTitle(title);
        } else if (Objects.nonNull(year1)
                && Objects.isNull(year2)
        ) {
            // find by title year (title can be null)
            return movieService.getByTitleYear(title, year1);

        } else if (Objects.isNull(title)
                && Objects.nonNull(year1)
                && Objects.nonNull(year2)
        ) {
            // find by range year
            return movieService.getByRangeYear(year1, year2);
        } else if (Objects.nonNull(title)
                && Objects.nonNull(year1)
                && Objects.nonNull(year1)
        ) {
            // find by title and range year
            return movieService.getByTitleRangeYear(title, year1, year2);
        } else {
            // no criteria : too wide => error or empty list
            return Set.of();
        }
    }

    @GetMapping("/byActor")
    public Set<MovieDto> getByActor(@RequestParam("n") String actorName) {
        return movieService.getByActor(actorName);
    }

    // @Valid not mandatory if spring-boot-starter-validation by default
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MovieDto addMovie(@RequestBody @Valid MovieDto movie){
        return movieService.addMovie(movie);
    }

}
