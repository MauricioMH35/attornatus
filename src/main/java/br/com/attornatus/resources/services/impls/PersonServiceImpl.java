package br.com.attornatus.resources.services.impls;

import br.com.attornatus.models.entities.Person;
import br.com.attornatus.resources.services.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final Logger log = LoggerFactory.getLogger(PersonServiceImpl.class);
    
    private final PersonServiceSave save;
    private final PersonServiceFindAll findAll;
    private final PersonServiceFindById findById;
    private final PersonServiceFindByNameContains findByNameContains;
    private final PersonServiceFindByBirth findByBirth;
    private final PersonServiceFindByBirthBetween findByBirthBetween;
    private final PersonServiceUpdateById updateById;
    private final PersonServiceDeleteById deleteById;

    @Override
    public Person save(Person person) {
        return save.apply(person);
    }

    @Override
    public Page<Person> findAll(Map<String, String> pageParams) {
        return findAll.apply(pageParams);
    }

    @Override
    public Person findById(Long id) {
        return findById.apply(id);
    }

    @Override
    public Page<Person> findByNameContains(String name, Map<String, String> pageParams) {
        return findByNameContains.apply(name, pageParams);
    }

    @Override
    public Page<Person> findByBirth(String birth, Map<String, String> pageParams) {
        return findByBirth.apply(birth, pageParams);
    }

    @Override
    public Page<Person> findByBirthBetween(Map<String, String> betweenParams, Map<String, String> pageParams) {
        return findByBirthBetween.apply(betweenParams, pageParams);
    }

    @Override
    public Boolean updateById(Long id, Person person) {
        return updateById.apply(id, person);
    }

    @Override
    public Boolean deleteById(Long id) {
        return deleteById.apply(id);
    }

}
