package br.com.attornatus.resources.services.impls;

import br.com.attornatus.exceptions.NotFoundException;
import br.com.attornatus.models.entities.Address;
import br.com.attornatus.models.repositories.AddressRepository;
import br.com.attornatus.resources.services.AddressServiceFindById;
import br.com.attornatus.resources.services.utils.IdServiceUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AddressServiceFindByIdImpl implements AddressServiceFindById {

    private final Logger log = LoggerFactory.getLogger(AddressServiceFindByIdImpl.class);
    private final IdServiceUtil idServiceUtil;
    private final AddressRepository repository;

    @Override
    public Address apply(Long id) {
        idServiceUtil.validIdentity(id);
        Optional<Address> founded = repository.findById(id);
        throwExceptionNotFound(founded);
        return founded.get();
    }

    private void throwExceptionNotFound(Optional<Address> founded) {
        if (founded.isEmpty()) {
            throw new NotFoundException("Não foi possivel encontrar o endereço.");
        }
    }


}
