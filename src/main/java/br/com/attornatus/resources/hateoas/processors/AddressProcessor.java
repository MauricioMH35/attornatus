package br.com.attornatus.resources.hateoas.processors;

import br.com.attornatus.models.entities.Address;
import br.com.attornatus.resources.hateoas.models.AddressModel;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.CollectionModel;

public interface AddressProcessor {

    AddressModel save(Address address);
    AddressModel findById(Address address);
    AddressModel findMainAddressByPersonId(Address address);
    CollectionModel<AddressModel> findAllByPersonId(Page<Address> address);
    AddressModel udpateById(Address address);

}
