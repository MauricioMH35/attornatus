package br.com.attornatus.resources.hateoas.processors.impls;

import br.com.attornatus.models.entities.Address;
import br.com.attornatus.resources.hateoas.assemblers.AddressHateoasAssembler;
import br.com.attornatus.resources.hateoas.models.AddressModel;
import br.com.attornatus.resources.hateoas.processors.AddressProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.CollectionModel;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AddressProcessorImpl implements AddressProcessor {

    private final AddressHateoasAssembler assembler;
    private final AddressModelProcessor modelHyperMedia;
    private final AddressCollectionProcessor collectionHyperMedia;

    @Override
    public AddressModel save(Address address) {
        AddressModel model = assembler.toModel(address);
        return modelHyperMedia.process(model);
    }

    @Override
    public AddressModel findById(Address address) {
        AddressModel model = assembler.toModel(address);
        return modelHyperMedia.process(model);
    }

    @Override
    public AddressModel findMainAddressByPersonId(Address address) {
        AddressModel model = assembler.toModel(address);
        return modelHyperMedia.process(model);
    }

    @Override
    public CollectionModel<AddressModel> findAllByPersonId(Page<Address> address) {
        CollectionModel<AddressModel> models = assembler.toCollectionModel(address);
        return collectionHyperMedia.process(models);
    }

    @Override
    public AddressModel udpateById(Address address) {
        AddressModel model = assembler.toModel(address);
        return modelHyperMedia.process(model);
    }

}
