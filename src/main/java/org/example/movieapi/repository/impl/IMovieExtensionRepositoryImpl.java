package org.example.movieapi.repository.impl;

import org.example.movieapi.entity.Movie;
import org.example.movieapi.entity.Movie_;
import org.example.movieapi.entity.Person_;
import org.example.movieapi.repository.IMovieExtensionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.JoinType;
import java.util.stream.Stream;

public class IMovieExtensionRepositoryImpl implements IMovieExtensionRepository {

    // @Autowired
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Stream<Movie> findByDirectorNameCB(String directorName) {
        var cb = entityManager.getCriteriaBuilder();
        var q = cb.createQuery(Movie.class);
        var rootMovie = q.from(Movie.class);
        rootMovie.fetch(Movie_.director, JoinType.INNER);
        //var director = rootMovie.join(Movie_.director);
        q.select(rootMovie)
                .where(cb.equal(
                        cb.lower(rootMovie.get(Movie_.director).get(Person_.name)),
                        // director.get(Person_.name)), // director join
                        directorName.toLowerCase()));
        return entityManager.createQuery(q)
                .getResultStream();
    }
}
