package br.com.attornatus.resources.hateoas.processors.impls;

import br.com.attornatus.resources.hateoas.models.AddressModel;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AddressCollectionProcessor {

    private final DefaultAddressProcessor hyperMedia;

    public CollectionModel<AddressModel> process(CollectionModel<AddressModel> models) {
        for (AddressModel model : models) {
            hyperMedia.addLinkFindById(model);
            hyperMedia.addLinkUpdateById(model);
            hyperMedia.addLinkDeleteByid(model);
            hyperMedia.addLinkFindMainAddressByPersonId(model);
        }
        return models;
    }

}
