package org.example.movieapi.dto;

import lombok.*;
import org.example.movieapi.entity.Person;
import org.example.movieapi.enums.ColorEnum;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
// @Builder
@Getter @Setter
public class MovieDetailDto extends MovieDto {
    private PersonDto director;
    private Set<PersonDto> actors;
}
