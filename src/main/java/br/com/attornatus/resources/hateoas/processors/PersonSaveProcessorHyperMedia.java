package br.com.attornatus.resources.hateoas.processors;

import br.com.attornatus.resources.controllers.AddressController;
import br.com.attornatus.resources.controllers.PersonController;
import br.com.attornatus.resources.hateoas.models.PersonModel;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
@RequiredArgsConstructor
public class PersonSaveProcessorHyperMedia {

    private final DefaultPersonLinkHyperMedia linkHyperMedia;

    public PersonModel process(PersonModel model) {
        linkHyperMedia.addLinkFindById(model);
        linkHyperMedia.addLinkUpdateById(model);
        linkHyperMedia.addLinkDeleteById(model);
        linkHyperMedia.addLinkAddAddressByPersonId(model);
        linkHyperMedia.addLinkFindAll(model);
        return model;
    }

}
