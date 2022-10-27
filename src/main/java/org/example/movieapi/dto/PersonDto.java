package org.example.movieapi.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
public class PersonDto {
    private Integer id;

    @NotBlank
    private String name;

    private LocalDate birthdate;
}
