package br.com.attornatus.resources.services;

import br.com.attornatus.models.entities.Address;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface AddressServiceFindAllByPersonId {

    Page<Address> apply(Long personId, Map<String, String> pageParams);

}
