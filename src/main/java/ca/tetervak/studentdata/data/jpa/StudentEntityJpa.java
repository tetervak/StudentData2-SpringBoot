package ca.tetervak.studentdata.data.jpa;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "student")
@NoArgsConstructor
@Getter
@Setter
public class StudentEntityJpa implements Serializable {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "first_name")
    private String firstName = "";

    @Column(name = "last_name")
    private String lastName = "";

    @Column(name = "program_name")
    private String programName = "";

    @Column(name = "program_year")
    private Integer programYear = 0;

    @Column(name = "program_coop")
    private Boolean programCoop = false;

    @Column(name = "program_internship")
    private Boolean programInternship = false;
}
