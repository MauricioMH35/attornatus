package br.com.attornatus.resources.hateoas.assemblers;

import br.com.attornatus.models.entities.Person;
import br.com.attornatus.resources.controllers.PersonController;
import br.com.attornatus.resources.hateoas.models.PersonModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

import java.util.ArrayList;
import java.util.List;

public class PersonHateoasAssembler extends RepresentationModelAssemblerSupport<Person, PersonModel> {

    public PersonHateoasAssembler() {
        super(PersonController.class, PersonModel.class);
    }

    @Override
    public PersonModel toModel(Person entity) {
        return PersonModel.toModel(entity);
    }

    @Override
    public CollectionModel<PersonModel> toCollectionModel(Iterable<? extends Person> entities) {
        Iterable<PersonModel> personModels = parseEntityByIterable(entities);
        return CollectionModel.of(personModels);
    }

    private Iterable<PersonModel> parseEntityByIterable(Iterable<? extends Person> entities) {
        List<PersonModel> personModels = new ArrayList<>();
        for (Person entity : entities) {
            personModels.add(PersonModel.toModel(entity));
        }
        return personModels;
    }

}
