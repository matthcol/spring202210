package org.example.movieapi.entity;

import javax.persistence.*;

@Entity
public class Movie {
    private Integer id;
    private String title;
    private short year;
    private Short duration;

    public Movie() {
    }

    public Movie(String title, short year) {
        this.title = title;
        this.year = year;
    }

    public Movie(Integer id, String title, short year, Short duration) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.duration = duration;
    }

    @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public short getYear() {
        return year;
    }

    public void setYear(short year) {
        this.year = year;
    }

    @Transient
    public Short getDuration() {
        return duration;
    }

    public void setDuration(Short duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", year=" + year +
                ", duration=" + duration +
                '}';
    }
}
