package br.com.attornatus.resources.services;

import br.com.attornatus.models.entities.Address;
import org.springframework.stereotype.Service;

@Service
public interface AddressServiceUpdateById {

    Address apply(Long id, Address address);

}
