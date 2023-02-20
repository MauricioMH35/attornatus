package br.com.attornatus.resources.hateoas.processors.impls;

import br.com.attornatus.resources.hateoas.models.PersonModel;
import br.com.attornatus.resources.hateoas.processors.impls.DefaultPersonProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PersonModelProcessor {

    private final DefaultPersonProcessor linkHyperMedia;

    public PersonModel simpleProccess(PersonModel model) {
        linkHyperMedia.addLinkFindById(model);
        linkHyperMedia.addLinkUpdateById(model);
        linkHyperMedia.addLinkDeleteById(model);
        linkHyperMedia.addLinkSave(model);
        linkHyperMedia.addLinkAddAddressByPersonId(model);
        return model;
    }

    public PersonModel process(PersonModel model) {
        linkHyperMedia.addLinkUpdateById(model);
        linkHyperMedia.addLinkDeleteById(model);
        linkHyperMedia.addLinkAddAddressByPersonId(model);
        linkHyperMedia.addLinkFindMainAddressByPersonId(model);
        linkHyperMedia.addLinkFindAll(model);
        return model;
    }

}
