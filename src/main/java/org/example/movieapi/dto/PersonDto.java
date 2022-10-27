package org.example.movieapi.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PersonDto {
    private Integer id;
    private String name;
    private LocalDate birthdate;
}
