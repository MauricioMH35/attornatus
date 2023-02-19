package br.com.attornatus.resources.hateoas.processors;

import br.com.attornatus.resources.hateoas.models.PersonModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PersonModelProcessorHyperMedia {

    private final DefaultPersonLinkHyperMedia linkHyperMedia;

    public PersonModel process(PersonModel model) {
        // todo : implementar a adição dos links hyper media ao modelo
        return model;
    }

}
