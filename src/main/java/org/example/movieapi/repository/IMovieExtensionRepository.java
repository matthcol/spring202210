package org.example.movieapi.repository;

import org.example.movieapi.entity.Movie;

import java.util.stream.Stream;

public interface IMovieExtensionRepository {

    Stream<Movie> findByDirectorNameCB(String directorName);

}
