package org.example.movieapi.utils;

import net.bytebuddy.utility.RandomString;
import org.springframework.data.domain.Sort;

import java.util.stream.Stream;

public class Generators {

    public static Stream<String> titleTooLong() {
        return Stream.of(RandomString.make(301));
    }

    public static Stream<Sort> movieSorts() {
        return Stream.of(
                Sort.by("year", "title"),
                Sort.by(Sort.Order.desc("year"), Sort.Order.asc("title"))
        );
    }
}
