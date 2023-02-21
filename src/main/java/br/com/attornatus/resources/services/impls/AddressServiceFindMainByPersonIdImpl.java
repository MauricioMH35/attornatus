package br.com.attornatus.resources.services.impls;

import br.com.attornatus.exceptions.NotFoundException;
import br.com.attornatus.models.entities.Address;
import br.com.attornatus.models.repositories.AddressRepository;
import br.com.attornatus.resources.services.AddressServiceFindMainByPersonId;
import br.com.attornatus.resources.services.utils.IdServiceUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AddressServiceFindMainByPersonIdImpl implements AddressServiceFindMainByPersonId {

    private final Logger log = LoggerFactory.getLogger(AddressServiceFindMainByPersonIdImpl.class);
    private final IdServiceUtil idServiceUtil;
    private final AddressRepository repository;

    @Override
    public Address apply(Long personId) {
        idServiceUtil.validIdentity(personId);
        Address founded = repository.findHighRelevanceLevelByPersonId(personId);
        throwExceptionNotFoundBtPersonId(founded);
        return founded;
    }

    private void throwExceptionNotFoundBtPersonId(Address founded) {
        if (founded == null) {
            throw new NotFoundException("Não foi possivel encontrar o endereço principal da pessoa indicada.");
        }
    }

}
