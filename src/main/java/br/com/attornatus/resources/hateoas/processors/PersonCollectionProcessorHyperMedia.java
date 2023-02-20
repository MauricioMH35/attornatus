package br.com.attornatus.resources.hateoas.processors;

import br.com.attornatus.models.entities.Person;
import br.com.attornatus.resources.hateoas.models.PersonModel;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PersonCollectionProcessorHyperMedia {

    private final DefaultPersonLinkHyperMedia linkHyperMedia;

    public CollectionModel<PersonModel> process(CollectionModel<PersonModel> models) {
        linkHyperMedia.addLinkSave(models);
        linkHyperMedia.addLinkFindByBirth(models);
        linkHyperMedia.addLinkFindByBirthBetween(models);

        for (PersonModel model : models) {
            linkHyperMedia.addLinkFindById(model);
            linkHyperMedia.addLinkFindMainAddressByPersonId(model);
        }

        return models;
    }

}
