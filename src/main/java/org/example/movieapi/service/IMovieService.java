package org.example.movieapi.service;

import org.example.movieapi.dto.MovieDetailDto;
import org.example.movieapi.dto.MovieDto;
import org.example.movieapi.entity.Movie;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public interface IMovieService {
    List<MovieDto> getAll();

    Optional<MovieDetailDto> getById(int id);

    Set<MovieDto> getByTitle(String title);
    Set<MovieDto> getByTitleYear(String title, Short year);
    Set<MovieDto> getByTitleRangeYear(String title, Short year1, Short year2);
    Set<MovieDto> getByRangeYear(Short year1, Short year2);
    Set<MovieDto> getByYear(Short year);

    Set<MovieDto> getByActor(String actorName);

    MovieDto addMovie(MovieDto movie);
}
