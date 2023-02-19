package br.com.attornatus.resources.hateoas.processors;

import br.com.attornatus.resources.hateoas.models.PersonModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PersonModelProcessorHyperMedia {

    private final DefaultPersonLinkHyperMedia linkHyperMedia;

    public PersonModel process(PersonModel model) {
        linkHyperMedia.addLinkUpdateById(model);
        linkHyperMedia.addLinkDeleteById(model);
        linkHyperMedia.addLinkFindMainAddressByPersonId(model);
        linkHyperMedia.addLinkAddAddressByPersonId(model);
        linkHyperMedia.addLinkFindAll(model);
        return model;
    }

}
