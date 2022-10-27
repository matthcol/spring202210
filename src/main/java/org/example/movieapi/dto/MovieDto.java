package org.example.movieapi.dto;

import lombok.*;
import org.example.movieapi.enums.ColorEnum;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Builder
@Getter
@Setter
public class MovieDto {
    private Integer id;

    @NotBlank
    private String title;

    @Min(1850)
    private Short year;

    @Min(45)
    private Short duration;
    private String posterUri;
    private ColorEnum color;
    private Set<String> genres;
}
