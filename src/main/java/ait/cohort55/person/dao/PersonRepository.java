package ait.cohort55.person.dao;

import ait.cohort55.person.dto.CityPopulationDto;
import ait.cohort55.person.dto.PersonDto;
import ait.cohort55.person.model.Child;
import ait.cohort55.person.model.Employee;
import ait.cohort55.person.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

public interface PersonRepository extends JpaRepository<Person, Integer> {

    Stream<Person> findPersonByNameIgnoreCase(String name);


    Stream<Person> findPersonByAddressCityIgnoreCase(String city);

    Stream<Person> findPersonByBirthDateBetween(LocalDate from, LocalDate to);

    @Query("select new ait.cohort55.person.dto.CityPopulationDto(p.address.city, count(p))"+
            " from Person p group by p.address.city order by count(p) desc")
    List<CityPopulationDto> getCitiesPopulation();

    @Query("select c from Child c order by c.name")
    Stream<Child> findAllChildrenOrderByByName();

    @Query("select e from Employee e where e.salary between :min and :max")
    Stream<Employee> findEmployeesBySalaryBetween(int min, int max);

}

