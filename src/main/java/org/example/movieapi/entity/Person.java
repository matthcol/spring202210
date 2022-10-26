package org.example.movieapi.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

// TODO: question: strategy can be set globally ?

@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Getter @Setter
@ToString
@Entity
@Table(name="persons")
public class Person {

    @Id
    // @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @GeneratedValue(generator = "seq_person_id") //strategy = GenerationType.SEQUENCE,
    @SequenceGenerator(name = "seq_person_id",
            sequenceName = "seq_person_id")
    private Integer id;

    @NonNull // RequiredArgsConstructor
    @Column(nullable = false)
    private String name;

    // old date type java 1.0
    // @Temporal(TemporalType.DATE)
    // private Date birthdate;

    // old date typejava 1.1
    // @Temporal(TemporalType.DATE)
    //private Calendar birthdate;

    LocalDate birthdate;
}
