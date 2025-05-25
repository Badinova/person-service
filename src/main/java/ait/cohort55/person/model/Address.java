package ait.cohort55.person.model;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Address implements Serializable {
    private String city;
    private String street;
    private int building;
}
