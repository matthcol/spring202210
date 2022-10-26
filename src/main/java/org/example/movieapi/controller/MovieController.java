package org.example.movieapi.controller;

import org.example.movieapi.entity.Movie;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    @GetMapping
    public Movie get() {
        return new Movie("Top Gun: Maverick", (short) 2022);
    }

}
