package br.com.attornatus.resources.services;

import br.com.attornatus.models.entities.Person;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface PersonService {

    Person save(Person person);

    Page<Person> findAll(Map<String, String> pageParams);

    Person findById(Long id);

    Page<Person> findByNameContains(String name, Map<String, String> pageParams);

    Page<Person> findByBirth(String birth, Map<String, String> pageParams);

    Page<Person> findByBirthBetween(Map<String, String> betweenParams, Map<String, String> pageParams);

    Boolean updateById(Long id, Person person);

    Boolean deleteById(Long id);

}
