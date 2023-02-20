package br.com.attornatus.resources.hateoas.processors.impls;

import br.com.attornatus.resources.hateoas.models.PersonModel;
import br.com.attornatus.resources.hateoas.processors.impls.DefaultPersonProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PersonCollectionProcessor {

    private final DefaultPersonProcessor linkHyperMedia;

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
