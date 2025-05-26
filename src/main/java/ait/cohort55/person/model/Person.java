package ait.cohort55.person.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "persons")
@Inheritance(strategy = InheritanceType.JOINED)
public class Person implements Serializable {
    @Id
    private int id;
    @Setter
    private String name;
    private LocalDate birthDate;
    @Setter
    private Address address;
}
