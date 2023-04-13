package org.example.movieapi.service.impl;

import org.example.movieapi.dto.MovieDetailDto;
import org.example.movieapi.dto.MovieDto;
import org.example.movieapi.entity.Movie;
import org.example.movieapi.entity.Person;
import org.example.movieapi.repository.IMovieRepository;
import org.example.movieapi.repository.IPersonRepository;
import org.example.movieapi.service.IMovieService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Transactional
@Service
public class MovieServiceImpl implements IMovieService {

    @Autowired
    IMovieRepository movieRepository;

    @Autowired
    IPersonRepository personRepository;

    @Autowired
    ModelMapper modelMapper;

//    public List<Movie> getAll() {
////        return Stream.of(
////                new Movie("Top Gun", (short) 1986),
////                new Movie("Top Gun: Maverick", (short) 2022)
////        );
//        return movieRepository.findAll();
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<Movie> getById(@PathVariable int id) {
////        var movie = new Movie("Top Gun: Maverick", (short) 2022);
////        movie.setId(id);
////        return movie;
//        var movieOpt = movieRepository.findById(id);
//        return movieOpt.map(ResponseEntity::ok)
//                .orElseGet(() -> ResponseEntity.notFound().build());
//    }
//
//    @GetMapping("/byTitle")
//    public Set<Movie> getByTitle(@RequestParam("t") String title){
//        return  movieRepository.findByTitleIgnoringCase(title)
//                // .collect(Collectors.toCollection(HashSet::new));
//                .collect(Collectors.toSet());
//    }
//
//    // @RequestParam can tuned with name, required and defaultValue
//    @GetMapping("/search")
//    public List<Movie> getByTitleOrYear(
//            @RequestParam(name = "t", required = false) String title,
//            @RequestParam(name = "y", required = false) Short year1,
//            @RequestParam(name = "y2", required = false) Short year2)
//    {
//        if (Objects.nonNull(title)
//                && Objects.isNull(year1)
//                && Objects.isNull(year2)
//        ) {
//            // find by title only
//            return movieRepository.findByTitleIgnoringCase(title).toList();
//        } else if (Objects.nonNull(year1)
//                && Objects.isNull(year2)
//        ) {
//            // find by title year (title can be null)
//            var movieExampleTitleYear = new Movie(title, year1);
//            return movieRepository.findAll(Example.of(movieExampleTitleYear,
//                    ExampleMatcher.matching().withIgnoreCase("title")));
//
//        } else if (Objects.isNull(title)
//                && Objects.nonNull(year1)
//                && Objects.nonNull(year2)
//        ) {
//            // find by range year
//            return movieRepository.findByYearBetween(year1, year2);
//        } else if (Objects.nonNull(title)
//                && Objects.nonNull(year1)
//                && Objects.nonNull(year1)
//        ) {
//            // find by title and range year
//            return movieRepository.findByTitleIgnoringCaseAndYearBetween(title, year1, year2);
//        } else {
//            // no criteria : too wide => error or empty list
//            return List.of();
//        }
//    }
//
//    @GetMapping("/byActor")
//    public List<Movie> getByActor(@RequestParam("n") String actorName) {
//        return movieRepository.findByActorsNameEndingWithIgnoreCase(actorName);
//    }
//
//    // @Valid not mandatory if spring-boot-starter-validation by default
//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    public Movie addMovie(@RequestBody @Valid Movie movie){
//        return movieRepository.save(movie);
//    }


    @Override
    public List<MovieDto> getAll() {
        return movieRepository.findAll().stream()
                .map(me -> modelMapper.map(me, MovieDto.class))
                .toList();
    }

    @Override
    public Optional<MovieDetailDto> getById(int id) {
        return movieRepository.findById(id)
            .map(me -> modelMapper.map(me, MovieDetailDto.class));
    }

    @Override
    public Set<MovieDto> getByTitle(String title) {
        return movieRepository.findByTitleContainingIgnoringCase(title)
                .map(me -> modelMapper.map(me, MovieDto.class))
                .collect(Collectors.toSet());
    }

    @Override
    public Set<MovieDto> getByTitleYear(String title, Short year) {
        var movieExampleTitleYear = new Movie(title, year);
        return movieRepository.findAll(Example.of(movieExampleTitleYear,
                    ExampleMatcher.matching().withIgnoreCase("title")))
                .stream()
                .map(me -> modelMapper.map(me, MovieDto.class))
                .collect(Collectors.toSet());
    }

    @Override
    public Set<MovieDto> getByTitleRangeYear(String title, Short year1, Short year2) {
        return movieRepository.findByTitleIgnoringCaseAndYearBetween(title, year1, year2)
                .stream()
                .map(me -> modelMapper.map(me, MovieDto.class))
                .collect(Collectors.toSet());
    }

    @Override
    public Set<MovieDto> getByRangeYear(Short year1, Short year2) {
        return movieRepository.findByYearBetween(year1, year2)
                .stream()
                .map(me -> modelMapper.map(me, MovieDto.class))
                .collect(Collectors.toSet());
    }

    @Override
    public Set<MovieDto> getByYear(Short year) {
        var movieExampleYear = new Movie(null, year);
        return movieRepository.findAll(Example.of(movieExampleYear))
                .stream()
                .map(me -> modelMapper.map(me, MovieDto.class))
                .collect(Collectors.toSet());
    }


    @Override
    public Set<MovieDto> getByActor(String actorName) {
        return movieRepository.findByActorsNameEndingWithIgnoreCase(actorName)
                .stream()
                .map(me -> modelMapper.map(me, MovieDto.class))
                .collect(Collectors.toSet());
    }

    @Override
    public MovieDto addMovie(MovieDto movieDto) {
        var movieEntity = modelMapper.map(movieDto, Movie.class);
        movieRepository.saveAndFlush(movieEntity);
        return modelMapper.map(movieEntity, MovieDto.class);
    } // commit

    @Override
    public Optional<MovieDetailDto> setDirector(int movieId, int directorId) {
        var movieEntityOpt = movieRepository.findById(movieId);
        var directorEntityOpt = personRepository.findById(directorId);
        return movieEntityOpt.flatMap(
                me -> directorEntityOpt.map(
                        de -> {
                            me.setDirector(de);  // SQL: update
                            return modelMapper.map(me, MovieDetailDto.class);
                        }
                )
        );
    }

    @Override
    public Optional<MovieDetailDto> setActors(int idMovie, List<Integer> idActors) {
        return movieRepository.findById(idMovie) // fetch movie
                .map(me -> {
                    // fetch actors
                    var actorEntities = personRepository.findAllById(idActors);
                    if (actorEntities.size() !=  idActors.size()) {
                        // cancel update
                        return null;
                    }
                    me.getActors().clear();;
                    me.getActors().addAll(actorEntities);
                    return modelMapper.map(me, MovieDetailDto.class);
                });
    }

    @Override
    public boolean deleteMovie(Integer idMovie) {
        // movieRepository.deleteById(idMovie);
        return movieRepository.findById(idMovie)
                .map(me -> {
                    movieRepository.delete(me);
                    return true;
                }).orElse(false);
    } // commit
}
