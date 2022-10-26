package org.example.movieapi.hibernate.criteria;

import org.example.movieapi.entity.Movie;
import org.example.movieapi.entity.Movie_;
import org.example.movieapi.entity.Person_;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityManager;
import javax.persistence.criteria.JoinType;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("testq")
public class MovieCriteriaQueryTest {

    @Autowired
    EntityManager entityManager;

    @Test
    void testMovieByDirector(){
        String directorName = "clinT eastWooD";
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
        entityManager.createQuery(q)
                .getResultStream()
                .forEach(m -> System.out.println(
                        "\t - " + m.getTitle()
                        + "(" + m.getYear()
                        + ")" + m.getDirector().getName()));

    }
}
