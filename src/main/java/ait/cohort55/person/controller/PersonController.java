package ait.cohort55.person.controller;

import ait.cohort55.person.dto.AddressDto;
import ait.cohort55.person.dto.CityPopulationDto;
import ait.cohort55.person.dto.PersonDto;
import ait.cohort55.person.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/person")
@RequiredArgsConstructor
public class PersonController {
    private final PersonService personService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addPerson(@RequestBody PersonDto personDto) {
        personService.addPerson(personDto);
    }

    @GetMapping("/{id}")
    public PersonDto getPersonById(@PathVariable Integer id) {
        return personService.getPersonById(id);
    }

    @DeleteMapping("/{id}")
    public PersonDto deletePersonById(@PathVariable Integer id) {
        return personService.deletePersonById(id);
    }

    @PatchMapping("/{id}/name/{name}")
    public PersonDto updatePerson(@PathVariable Integer id, @PathVariable String name) {
        return personService.updatePerson(id, name);
    }

    @PatchMapping("/{id}/address")
    public PersonDto updatePersonAddress(@PathVariable Integer id, @RequestBody AddressDto addressDto) {
        return personService.updatePersonAddress(id, addressDto);
    }

    @GetMapping("/name/{name}")
    public PersonDto[] findPersonsByName(@PathVariable String name) {
        return personService.findPersonsByName(name);
    }

    @GetMapping("/city/{city}")
    public PersonDto[] findPersonsByCity(@PathVariable String city) {
        return personService.findPersonsByCity(city);
    }

    @GetMapping("/ages/{minAge}/{maxAge}")
    public PersonDto[] findPersonsByBetweenAge(@PathVariable Integer minAge, @PathVariable Integer maxAge) {
        return personService.findPersonsByBetweenAge(minAge, maxAge);
    }

    @GetMapping("/population/city")
    public Iterable<CityPopulationDto> getCitiesPopulation() {
        return personService.getCitiesPopulation();
    }
}
