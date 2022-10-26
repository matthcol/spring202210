package org.example.movieapi.repository;

import org.example.movieapi.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPersonRepository extends JpaRepository<Person, Integer> {

}
