package org.example.movieapi.dto;

import lombok.*;
import org.example.movieapi.enums.ColorEnum;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Builder
@Getter
@Setter
public class MovieDto {
    private Integer id;
    private String title;
    private Short year;
    private Short duration;
    private String posterUri;
    private ColorEnum color;
    private Set<String> genres;
}
