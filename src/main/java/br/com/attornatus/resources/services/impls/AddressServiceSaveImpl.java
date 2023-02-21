package br.com.attornatus.resources.services.impls;

import br.com.attornatus.exceptions.InternalServerErrorException;
import br.com.attornatus.models.entities.Address;
import br.com.attornatus.models.enums.AddressRelevanceLevel;
import br.com.attornatus.models.repositories.AddressRepository;
import br.com.attornatus.resources.services.AddressServiceSave;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AddressServiceSaveImpl implements AddressServiceSave {

    private final Logger log = LoggerFactory.getLogger(AddressServiceSaveImpl.class);
    private final AddressRepository repository;

    @Override
    public Address apply(Address address) {
        Long personId = address.getPerson().getId();
        AddressRelevanceLevel level = address.getRelevanceLevel();
        throwExceptionExistMainAddressByPersonId(personId, level);

        Address saved = repository.save(address);
        throwsExceptionNotToSave(saved);
        return saved;
    }

    private void throwExceptionExistMainAddressByPersonId(Long personId, AddressRelevanceLevel level) {
        if (level.equals(AddressRelevanceLevel.HIGH)) {
            Address founded = repository.findHighRelevanceLevelByPersonId(personId);

            if (founded != null) {
                throw new IllegalArgumentException("Uma pessoa não pode ter mais de um endereço principal.");
            }
        }
    }

    private void throwsExceptionNotToSave(Address address) {
        if (address == null) {
            throw new InternalServerErrorException("Não foi possivel completar o cadastro do endereço para a pessoa " +
                    "com o id informado devido ao erro interno.");
        }
    }

}
