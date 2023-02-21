package br.com.attornatus.resources.services.impls;

import br.com.attornatus.models.entities.Address;
import br.com.attornatus.resources.services.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class AddressService {

    private final Logger log = LoggerFactory.getLogger(AddressService.class);

    private final AddressServiceSave save;
    private final AddressServiceFindById findId;
    private final AddressServiceFindMainByPersonId findMainByPersonId;
    private final AddressServiceFindAllByPersonId findAllByPersonId;
    private final AddressServiceUpdateById updateById;
    private final AddressServiceDeleteById deleteById;

    public Address save(Address address) {
        return save.apply(address);
    }

    public Address findById(Long id) {
        return findId.apply(id);
    }

    public Address findMainAddressByPersonId(Long personId) {
        return findMainByPersonId.apply(personId);
    }

    public Page<Address> findAllByPersonId(Long personId, Map<String, String> pageParams) {
        return findAllByPersonId.apply(personId, pageParams);
    }

    public Address updateById(Long id, Address address) {
        return updateById.apply(id, address);
    }

    public Boolean deleteById(Long id) {
        return deleteById.apply(id);
    }

}
