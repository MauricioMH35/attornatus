package br.com.attornatus.resources.services.impls;

import br.com.attornatus.exceptions.NotFoundException;
import br.com.attornatus.models.entities.Address;
import br.com.attornatus.models.repositories.AddressRepository;
import br.com.attornatus.resources.services.AddressServiceFindAllByPersonId;
import br.com.attornatus.resources.services.utils.IdServiceUtil;
import br.com.attornatus.resources.services.utils.PageableServiceUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class AddressServiceFindAllByPersonIdImpl implements AddressServiceFindAllByPersonId {

    private final Logger log = LoggerFactory.getLogger(AddressServiceFindAllByPersonIdImpl.class);
    private final IdServiceUtil idServiceUtil;
    private final PageableServiceUtil pageableServiceUtil;
    private final AddressRepository repository;

    @Override
    public Page<Address> apply(Long personId, Map<String, String> pageParams) {
        idServiceUtil.validIdentity(personId);
        Pageable pageable = pageableServiceUtil
                .targetSort("publicPlace")
                .sortType(PageableServiceUtil.SortType.ASCENDING)
                .buildPageable(pageParams);

        Page<Address> founded = repository.findByPersonId(personId, pageable);
        throwExceptionNotFoundByPersonId(founded);
        return founded;
    }

    private void throwExceptionNotFoundByPersonId(Page<Address> founded) {
        if (founded.isEmpty()) {
            throw new NotFoundException("Não foi possivel encontrar os endereços referente a pessoa indicado pelo id.");
        }
    }

}
