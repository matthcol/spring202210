package org.example.movieapi.dto;

import org.example.movieapi.enums.ColorEnum;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class MovieDtoTest {

    @Test
    void testNoArgsConstructor(){
        var movie = new MovieDto();
    }

    @Test
    void testAllArgsConstructor(){
        var movie = MovieDto.of(
                1,
                "Top Gun: Maverick",
                (short) 2022,
                (short) 122,
                "http://super_poster",
                ColorEnum.COLOR,
                Set.of("Drama","Action")
        );
    }

    @Test
    void testBuilder() {
        var movie = MovieDto.builder()
                .id(1)
                .title("Top Gun: Maverick")
                .year((short) 2022)
                .duration((short) 122)
                .posterUri("http://super_poster")
                .build();
    }

}