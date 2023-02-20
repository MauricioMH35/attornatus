package br.com.attornatus.resources.hateoas.assemblers;

import br.com.attornatus.models.entities.Address;
import br.com.attornatus.resources.controllers.AddressController;
import br.com.attornatus.resources.hateoas.models.AddressModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

import java.util.ArrayList;
import java.util.List;

public class AddressHateoasAssembler extends RepresentationModelAssemblerSupport<Address, AddressModel> {

    public AddressHateoasAssembler() {
        super(AddressController.class, AddressModel.class);
    }

    @Override
    public AddressModel toModel(Address entity) {
        return AddressModel.toModel(entity);
    }

    @Override
    public CollectionModel<AddressModel> toCollectionModel(Iterable<? extends Address> entities) {
       Iterable<AddressModel> addressModels = parseEntitiesByIterable(entities);
       return CollectionModel.of(addressModels);
    }

    private Iterable<AddressModel> parseEntitiesByIterable(Iterable<? extends Address> entities) {
        List<AddressModel> addressModels = new ArrayList<>();
        for (Address entity : entities) {
            addressModels.add(AddressModel.toModel(entity));
        }
        return addressModels;
    }

}
