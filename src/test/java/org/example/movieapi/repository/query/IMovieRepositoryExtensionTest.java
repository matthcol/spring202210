package org.example.movieapi.repository.query;


import org.example.movieapi.repository.IMovieRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("testq")
public class IMovieRepositoryExtensionTest {

    @Autowired
    IMovieRepository movieRepository;

    @Test
    void testFindByDirectorNameCB(){
        String directorName = "clinT eastWooD";
        movieRepository.findByDirectorNameCB(directorName)
                .forEach(m -> System.out.println(
                        "\t - " + m.getTitle()
                                + "(" + m.getYear()
                                + ")" + m.getDirector().getName()));

    }
}
