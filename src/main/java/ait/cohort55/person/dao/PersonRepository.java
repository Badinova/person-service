package ait.cohort55.person.dao;

import ait.cohort55.person.dto.PersonDto;
import ait.cohort55.person.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Integer> {
    List<Person> findPersonByNameIgnoreCase(String name);
    List<Person> findPersonByAddressCityIgnoreCase(String city);
    List<Person> findPersonByBirthDateBetween(LocalDate from, LocalDate to);

}
