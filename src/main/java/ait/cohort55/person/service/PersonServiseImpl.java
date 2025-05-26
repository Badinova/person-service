package ait.cohort55.person.service;

import ait.cohort55.person.dao.PersonRepository;
import ait.cohort55.person.dto.AddressDto;
import ait.cohort55.person.dto.CityPopulationDto;
import ait.cohort55.person.dto.PersonDto;
import ait.cohort55.person.dto.exception.ConflictException;
import ait.cohort55.person.dto.exception.NotFoundException;
import ait.cohort55.person.model.Address;
import ait.cohort55.person.model.Person;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ConfigurationException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonServiseImpl implements PersonService {
    private final PersonRepository personRepository;
    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public void addPerson(PersonDto personDto) {
        if (personRepository.existsById(personDto.getId())) {
            throw new ConflictException("Person with id" + personDto.getId() + "already exists");
        }
        personRepository.save(modelMapper.map(personDto, Person.class));

    }

    @Override
    public PersonDto getPersonById(Integer id) {
        Person person = personRepository.findById(id).orElseThrow(NotFoundException::new);

        return modelMapper.map(person, PersonDto.class);
    }

    @Transactional
    @Override
    public PersonDto deletePersonById(Integer id) {
        Person person = personRepository.findById(id).orElseThrow(NotFoundException::new);
        personRepository.delete(person);
        return modelMapper.map(person, PersonDto.class);
    }

    @Transactional
    @Override
    public PersonDto updatePerson(Integer id, String name) {
        Person person = personRepository.findById(id).orElseThrow(NotFoundException::new);
        person.setName(name);
        return modelMapper.map(person, PersonDto.class);
    }

    @Transactional
    @Override
    public PersonDto updatePersonAddress(Integer id, AddressDto addresDto) {
        Person person = personRepository.findById(id).orElseThrow(NotFoundException::new);
        person.setAddress(modelMapper.map(addresDto, Address.class));
        return modelMapper.map(person, PersonDto.class);
    }

    @Transactional(readOnly = true)
    @Override
    public PersonDto[] findPersonsByName(String name) {
        return personRepository.findPersonByNameIgnoreCase(name)
                .map(p -> modelMapper.map(p, PersonDto.class))
                .toArray(PersonDto[]::new);
    }

    @Transactional(readOnly = true)
    @Override
    public PersonDto[] findPersonsByCity(String city) {
        return personRepository.findPersonByAddressCityIgnoreCase(city)
                .map(p -> modelMapper.map(p, PersonDto.class))
                .toArray(PersonDto[]::new);
    }

    @Transactional(readOnly = true)
    @Override
    public PersonDto[] findPersonsByBetweenAge(Integer minAge, Integer maxAge) {
        LocalDate from = LocalDate.now().minusYears(maxAge);
        LocalDate to = LocalDate.now().minusYears(minAge);
        return personRepository.findPersonByBirthDateBetween(from, to)
                .map(p -> modelMapper.map(p, PersonDto.class))
                .toArray(PersonDto[]::new);
    }

    @Override
    public Iterable<CityPopulationDto> getCitiesPopulation() {
        return personRepository.getCitiesPopulation();
    }
}
