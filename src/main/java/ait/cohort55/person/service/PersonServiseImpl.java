package ait.cohort55.person.service;

import ait.cohort55.person.dao.PersonRepository;
import ait.cohort55.person.dto.AddressDto;
import ait.cohort55.person.dto.CityPopulationDto;
import ait.cohort55.person.dto.PersonDto;
import ait.cohort55.person.dto.exception.ConflictException;
import ait.cohort55.person.dto.exception.NotFoundException;
import ait.cohort55.person.model.Address;
import ait.cohort55.person.model.Person;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ConfigurationException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonServiseImpl implements PersonService{
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

    @Override
    public PersonDto deletePersonById(Integer id) {
        Person person = personRepository.findById(id).orElseThrow(NotFoundException::new);
        personRepository.delete(person);
        return modelMapper.map(person, PersonDto.class);
    }

    @Override
    public PersonDto updatePerson(Integer id, String name) {
        Person person = personRepository.findById(id).orElseThrow(NotFoundException::new);
        String personName = person.getName();
        if (name != null && !name.equals(person.getName())) {
            person.setName(name);
        }
        return modelMapper.map(person, PersonDto.class);
    }

    @Override
    public PersonDto updatePersonAddress(Integer id, AddressDto addresDto) {
        Person person = personRepository.findById(id).orElseThrow(NotFoundException::new);
        Address newAddress = modelMapper.map(addresDto, Address.class);
        person.setAddress(newAddress);
        return modelMapper.map(person, PersonDto.class);
    }

    @Override
    public PersonDto[] findPersonsByName(String name) {
        List<Person> persons = personRepository.findPersonByNameIgnoreCase(name);
        return persons.stream()
                .map(person -> modelMapper.map(person, PersonDto.class))
                .toArray(PersonDto[]::new);
    }

    @Override
    public PersonDto[] findPersonsByCity(String city) {
        List<Person> persons = personRepository.findPersonByAddressCityIgnoreCase(city);
        return persons.stream()
                .map(person -> modelMapper.map(person, PersonDto.class))
                .toArray(PersonDto[]::new);
    }

    @Override
    public PersonDto[] findPersonsByBetweenAge(Integer minAge, Integer maxAge){
        if (minAge == null || maxAge == null || minAge < 0 || maxAge < 0 || minAge < maxAge) {
            throw new IllegalArgumentException("Incorrect age");
        }
        LocalDate now = LocalDate.now();
        LocalDate maxBirthDate = now.minusYears(minAge);
        LocalDate minBirthDate = now.minusYears(maxAge);
        List<Person> persons = personRepository.findPersonByBirthDateBetween(minBirthDate, maxBirthDate);

        return persons.stream()
                .map(person -> modelMapper.map(person, PersonDto.class))
                .toArray(PersonDto[]::new);
    }

    @Override
    public Iterable<CityPopulationDto> getCitiesPopulation() {
        return null;
    }
}
