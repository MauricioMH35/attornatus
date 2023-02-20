package br.com.attornatus.resources.hateoas.processors;

import br.com.attornatus.models.entities.Person;
import br.com.attornatus.resources.hateoas.models.PersonModel;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.CollectionModel;

public interface PersonProcessor {

    PersonModel save(Person person);
    PersonModel findById(Person person);
    PersonModel updateById(Person person);
    CollectionModel<PersonModel> findAll(Page<Person> persons);
    CollectionModel<PersonModel> findByNameContains(Page<Person> persons);
    CollectionModel<PersonModel> findByBirth(Page<Person> persons);
    CollectionModel<PersonModel> findByBirthBetween(Page<Person> persons);

}
