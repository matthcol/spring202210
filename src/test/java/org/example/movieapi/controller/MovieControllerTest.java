package org.example.movieapi.controller;

import org.example.movieapi.dto.MovieDetailDto;
import org.example.movieapi.dto.MovieDto;
import org.example.movieapi.service.IMovieService;
import org.example.movieapi.utils.JsonProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

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

    static final String BASE_URL = "/api/movies";

    @Test
    void getAll() {
    }

    @Test
    void getByIdOk() throws Exception {
        // TODO: generate id random
        int id = 1; // present in data persistence
        String title = "Top Gun: Maverick";
        short year = 2022;

        // prepare response from mock service
        var movie = new MovieDetailDto();
        movie.setId(id);
        movie.setTitle(title);
        movie.setYear(year);

        // eq important for object types (equals vs ==)
        given(movieService.getById(eq(id)))
                .willReturn(Optional.of(movie));

        mockMvc.perform(get(BASE_URL + "/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.title").value(title))
                .andExpect(jsonPath("$.year").value((int) year));

        then(movieService)
                .should()
                .getById(eq(id)); // eq important for object types (equals vs ==)

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
    void addMovie() throws Exception {
        // 1. given
        // properties for json in
        var title = "Nobody";
        short year = 2021;
        String movieJsonIn = JsonProvider.movieDtoJson(title, year);
        // perfect response from mock service
        int id = 1; // TODO: generate random int
        var movieFromService = MovieDto.builder()
                .id(id)
                .title(title)
                .year(year)
                .build();
        given(movieService.addMovie(any()))
                .willReturn(movieFromService);
        // 2. when/then
        mockMvc
                .perform(post(BASE_URL)	// build POST HTTP request
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(movieJsonIn)
                        .accept(MediaType.APPLICATION_JSON)) // + header request
                .andDo(print())	// intercept request to print
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.title").value(title))
                .andExpect(jsonPath("$.year").value((int) year));

        // check mock service has been called
        then(movieService)
                .should()
                .addMovie(any());
    }
}