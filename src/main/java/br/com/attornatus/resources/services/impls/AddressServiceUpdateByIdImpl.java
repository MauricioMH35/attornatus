package br.com.attornatus.resources.services.impls;

import br.com.attornatus.exceptions.InternalServerErrorException;
import br.com.attornatus.exceptions.NotFoundException;
import br.com.attornatus.models.entities.Address;
import br.com.attornatus.models.entities.Person;
import br.com.attornatus.models.enums.AddressRelevanceLevel;
import br.com.attornatus.models.repositories.AddressRepository;
import br.com.attornatus.resources.services.AddressServiceUpdateById;
import br.com.attornatus.resources.services.utils.IdServiceUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AddressServiceUpdateByIdImpl implements AddressServiceUpdateById {

    private final Logger log = LoggerFactory.getLogger(AddressServiceUpdateByIdImpl.class);
    private final IdServiceUtil idServiceUtil;
    private final AddressRepository repository;

    @Override
    public Address apply(Long id, Address address) {
        idServiceUtil.validIdentity(id);
        return update(id, address);
    }

    private Address update(Long id, Address update) {
        Optional<Address> founded = repository.findById(id);
        throwExceptionNotFoundById(founded);

        Address updatedFields = updateFields(founded.get(), update);
        Address updated = repository.save(updatedFields);
        throwExceptionNotUpdated(updated);
        return updated;
    }

    //region Update Fields
    private Address updateFields(Address founded, Address update) {
        Long id = founded.getId();
        AddressRelevanceLevel level = getRelevanceLevel(founded.getRelevanceLevel(), update.getRelevanceLevel());
        String publicPlace = getPublicPlace(founded.getPublicPlace(), update.getPublicPlace());
        String zipCode = getZipCode(founded.getZipCode(), update.getZipCode());
        Integer number = getNumber(founded.getNumber(), update.getNumber());
        String city = getCity(founded.getCity(), update.getCity());
        Person person = founded.getPerson();

        return Address.builder()
                .id(id)
                .relevanceLevel(level)
                .publicPlace(publicPlace)
                .zipCode(zipCode)
                .number(number)
                .city(city)
                .person(person)
                .build();
    }

    private AddressRelevanceLevel getRelevanceLevel(AddressRelevanceLevel founded,
                                                    AddressRelevanceLevel update) {
        return founded.equals(update) ? founded : update;
    }

    private String getPublicPlace(String founded, String update) {
        return founded.equals(update) ? founded : update;
    }

    private String getZipCode(String founded, String update) {
        return founded.equals(update) ? founded : update;
    }

    private Integer getNumber(Integer founded, Integer update) {
        return founded.equals(update) ? founded : update;
    }

    private String getCity(String founded, String update) {
        return founded.equals(update) ? founded : update;
    }
    //endregion

    private void throwExceptionNotFoundById(Optional<Address> founded) {
        if (founded.isEmpty()) {
            throw new NotFoundException("Não foi possivel encontrar o endereço informado pelo id.");
        }
    }

    private void throwExceptionNotUpdated(Address updated) {
        if (updated == null) {
            throw new InternalServerErrorException("Não foi possivel atualizar as informações do endereço.");
        }
    }

}
