package org.example.movieapi.entity;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class MovieTest {

    // session hibernate: cache objets
    @Autowired
    EntityManager entityManager;

    @Test
    void testSave(){
        String title = "Top Gun: Maverick";
        short year = 2022;
        var movie = new Movie(title, year);

        // obtain id
        // identity: call insert immediately
        // sequence: call sequence
        entityManager.persist(movie);
        // synchro cache hibernate
        // call insert here if not done before
        entityManager.flush();

        assertNotNull(movie.getId());
    }

}