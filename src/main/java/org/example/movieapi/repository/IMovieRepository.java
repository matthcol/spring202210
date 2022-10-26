package org.example.movieapi.repository;

import org.example.movieapi.dto.IDirectorStatDto;
import org.example.movieapi.entity.Movie;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.Tuple;
import java.util.stream.Stream;

// https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation
public interface IMovieRepository extends JpaRepository<Movie, Integer> {

    Stream<Movie> findByTitleIgnoringCase(String title);

    // find by director with name ending by (ignore cas)
    Stream<Movie> findByDirectorNameEndingWithIgnoreCase(
            String directorName,
            Sort sort);

    @Query("""
            select m
            from Movie m 
                inner join m.actors a
                left outer join fetch m.director
            where a.name = :actorName 
            and m.year between :year1 and :year2""")
    Stream<Movie> findByActorRangeYear(String actorName, short year1, short year2);


    @Query("""
            select 
                d.name as name, 
                COUNT(m.id) as count_movie, 
                MIN(m.year) as min_year,
                MAX(m.year) as max_year,
                COALESCE(SUM(m.duration), 0) as total_duration
            from Movie m 
            inner join m.director d
            where YEAR(d.birthdate) = :birthyear
            group by d.id, d.name
            order by count_movie desc
            """)
    Stream<Tuple> getStatByDirectorBorn(int birthyear);

    @Query("""
            select 
                d.name as name, 
                COUNT(m.id) as movieCount, 
                MIN(m.year) as minYear,
                MAX(m.year) as maxYear,
                COALESCE(SUM(m.duration), 0) as totalDuration
            from Movie m 
            inner join m.director d
            where YEAR(d.birthdate) = :birthyear
            group by d.id, d.name
            order by movieCount desc
            """)
    Stream<IDirectorStatDto> getStatByDirectorBorn2(int birthyear);


}
