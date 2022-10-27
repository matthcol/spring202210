package org.example.movieapi.controller;

import org.example.movieapi.dto.MovieDto;
import org.example.movieapi.service.IMovieService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.junit.jupiter.api.Assertions.*;

// start all controller components (Rest, MVC)
@WebMvcTest
class MovieControllerTest {

    // component under test: MovieController via mockMvc
    // - call HTTP
    // - data JSON
    @Autowired
    MockMvc mockMvc;

    @MockBean
    IMovieService movieService;

    @Test
    void getAll() {
    }

    @Test
    void getById() {
        mockMvc.perform(get());
    }

    @Test
    void getByTitle() {
    }

    @Test
    void getByTitleOrYear() {
    }

    @Test
    void getByActor() {
    }

    @Test
    void addMovie() {
    }
}