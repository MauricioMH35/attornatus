package br.com.attornatus.resources.hateoas.processors.impls;

import br.com.attornatus.models.entities.Person;
import br.com.attornatus.resources.hateoas.assemblers.PersonHateoasAssembler;
import br.com.attornatus.resources.hateoas.models.PersonModel;
import br.com.attornatus.resources.hateoas.processors.PersonProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.CollectionModel;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PersonProcessorImpl implements PersonProcessor {

    private final PersonHateoasAssembler assembler;
    private final PersonModelProcessor modelHyperMedia;
    private final PersonCollectionProcessor collectionHyperMedia;

    @Override
    public PersonModel save(Person person) {
        PersonModel model = assembler.toModel(person);
        return modelHyperMedia.simpleProccess(model);
    }

    @Override
    public PersonModel findById(Person person) {
        PersonModel model = assembler.toModel(person);
        return modelHyperMedia.process(model);
    }

    @Override
    public PersonModel updateById(Person person) {
        PersonModel model = assembler.toModel(person);
        return modelHyperMedia.process(model);
    }

    @Override
    public CollectionModel<PersonModel> findAll(Page<Person> persons) {
        CollectionModel<PersonModel> models = assembler.toCollectionModel(persons);
        return collectionHyperMedia.process(models);
    }

    @Override
    public CollectionModel<PersonModel> findByNameContains(Page<Person> persons) {
        CollectionModel<PersonModel> models = assembler.toCollectionModel(persons);
        return collectionHyperMedia.process(models);
    }

    @Override
    public CollectionModel<PersonModel> findByBirth(Page<Person> persons) {
        CollectionModel<PersonModel> models = assembler.toCollectionModel(persons);
        return collectionHyperMedia.process(models);
    }

    @Override
    public CollectionModel<PersonModel> findByBirthBetween(Page<Person> persons) {
        CollectionModel<PersonModel> models = assembler.toCollectionModel(persons);
        return collectionHyperMedia.process(models);
    }

}
