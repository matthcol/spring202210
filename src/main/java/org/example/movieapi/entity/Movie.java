package org.example.movieapi.entity;

import org.example.movieapi.enums.ColorEnum;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Entity
// Warning: table constarints with db names and not oop names
@Table(
        name="movies",
        uniqueConstraints = @UniqueConstraint(
            name = "uniq_title_year",
            columnNames = {"title", "year"}))
public class Movie {
    private Integer id;
    private String title;
    private short year;
    private Short duration;
    private ColorEnum color;

    private String posterUri;
    private Set<String> genres;

    private Person director;

    private Set<Person> actors;

    public Movie() {
        super();
        actors = new HashSet<>();
    }

    public Movie(String title, short year) {
        this();
        this.title = title;
        this.year = year;
    }

    public Movie(Integer id, String title, short year, Short duration, ColorEnum color) {
        this();
        this.id = id;
        this.title = title;
        this.year = year;
        this.duration = duration;
        this.color = color;
    }

    @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    @GeneratedValue(generator = "seq_movies_id") //strategy = GenerationType.SEQUENCE,
    @SequenceGenerator(name = "seq_movies_id",
        sequenceName = "seq_movies_id",
        allocationSize = 1)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @NotBlank
    @Column(length = 300, nullable = false)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Min(1850)
    // NB: with primitive type, NOT NULL implicit
    @Column(nullable = false)
    public short getYear() {
        return year;
    }

    public void setYear(short year) {
        this.year = year;
    }

    @Min(45)
    // @Transient // debug purpose
    // @Column(nullable = true) // default
    public Short getDuration() {
        return duration;
    }

    public void setDuration(Short duration) {
        this.duration = duration;
    }

    // NB: you can use a converter here
    @Enumerated(EnumType.STRING) // ORDINAL by default
    public ColorEnum getColor() {
        return color;
    }

    public void setColor(ColorEnum color) {
        this.color = color;
    }

    public String getPosterUri() {
        return posterUri;
    }

    public void setPosterUri(String posterUri) {
        this.posterUri = posterUri;
    }

    @ElementCollection
    @CollectionTable(name="have_genre",
            joinColumns = @JoinColumn(name = "movie_id")
    )
    @Column(name="genre", nullable = false, length = 20)
    public Set<String> getGenres() {
        return genres;
    }

    public void setGenres(Set<String> genres) {
        this.genres = genres;
    }

    // @Transient
    @ManyToOne //(fetch = FetchType.LAZY) // EAGER by default
    // NB: tuning with @JoinColumn: name, nullable
    public Person getDirector() {
        return director;
    }

    public void setDirector(Person director) {
        this.director = director;
    }


    @ManyToMany // fetch lazy
    @JoinTable(name = "play",
            joinColumns = @JoinColumn(name = "movie_id"), // to this entity
            inverseJoinColumns = @JoinColumn(name = "actor_id") // to other entity
    ) // check FK constraints
    public Set<Person> getActors() {
        return actors;
    }

    public void setActors(Set<Person> actors) {
        this.actors = actors;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Movie{");
        sb.append("id=").append(id);
        sb.append(", title='").append(title).append('\'');
        sb.append(", year=").append(year);
        sb.append(", duration=").append(duration);
        sb.append(", color=").append(color);
        sb.append('}');
        return sb.toString();
    }
}
