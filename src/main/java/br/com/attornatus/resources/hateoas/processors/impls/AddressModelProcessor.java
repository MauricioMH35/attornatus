package br.com.attornatus.resources.hateoas.processors.impls;

import br.com.attornatus.resources.hateoas.models.AddressModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AddressModelProcessor {

    private final DefaultAddressProcessor hyperMedia;

    public AddressModel process(AddressModel model) {
        hyperMedia.addLinkFindById(model);
        hyperMedia.addLinkUpdateById(model);
        hyperMedia.addLinkDeleteByid(model);
        hyperMedia.addLinkSave(model);
        return model;
    }

}
