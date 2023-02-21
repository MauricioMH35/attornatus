package br.com.attornatus.models.utils;

import br.com.attornatus.models.entities.Address;
import br.com.attornatus.models.entities.Person;
import br.com.attornatus.models.enums.AddressRelevanceLevel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

public class AddressUtil {

    public static Address newAddress() {
        return Address.builder()
                .relevanceLevel(AddressRelevanceLevel.MEDIUM)
                .publicPlace("Rua Pedralva")
                .zipCode("54480430")
                .number(554)
                .city("Jaboatão dos Guararapes")
                .person(Person.builder().id(1l).build())
                .build();
    }

    public static Address newAddressWithId() {
        return Address.builder()
                .id(1l)
                .relevanceLevel(AddressRelevanceLevel.MEDIUM)
                .publicPlace("Rua Pedralva")
                .zipCode("54480430")
                .number(554)
                .city("Jaboatão dos Guararapes")
                .person(PersonUtil.newEntity())
                .build();
    }

    public static Address newAddressByRelevanceLevel(AddressRelevanceLevel relevanceLevel) {
        return Address.builder()
                .relevanceLevel(relevanceLevel)
                .publicPlace("Rua Pedralva")
                .zipCode("54480430")
                .number(554)
                .city("Jaboatão dos Guararapes")
                .person(PersonUtil.newEntity())
                .build();
    }

    public static Address newAddressByRelevanceLevelWithId(AddressRelevanceLevel relevanceLevel) {
        return Address.builder()
                .id(1l)
                .relevanceLevel(relevanceLevel)
                .publicPlace("Rua Pedralva")
                .zipCode("54480430")
                .number(554)
                .city("Jaboatão dos Guararapes")
                .person(PersonUtil.newEntity())
                .build();
    }

    public static Page<Address> newAddressByPersonInPage() {
        return new PageImpl<>(List.of(newAddress()));
    }

    public static List<Address> newAddressesByPeople(Person people) {
        return List.of(
                Address.builder()
                        .id(1l)
                        .relevanceLevel(AddressRelevanceLevel.HIGH)
                        .publicPlace("Rua Pedralva")
                        .zipCode("54480430")
                        .number(554)
                        .city("Jaboatão dos Guararapes")
                        .person(people)
                        .build(),
                Address.builder()
                        .id(2l)
                        .relevanceLevel(AddressRelevanceLevel.MEDIUM)
                        .publicPlace("Rua Guanabara")
                        .zipCode("65056557")
                        .number(681)
                        .city("São Luís")
                        .person(people)
                        .build(),
                Address.builder()
                        .id(2l)
                        .relevanceLevel(AddressRelevanceLevel.LOW)
                        .publicPlace("Vila Mário de Andrade")
                        .zipCode("66032355")
                        .number(862)
                        .city("Belém")
                        .person(people)
                        .build()
        );
    }

    public static Page<Address> newAddressesByPeopleInPage(Person people) {
        return new PageImpl<Address>(newAddressesByPeople(people));
    }

    public static List<Address> newAddresses() {
        return List.of(
                Address.builder()
                        .id(1l)
                        .relevanceLevel(AddressRelevanceLevel.HIGH)
                        .publicPlace("Rua Pedralva")
                        .zipCode("54480430")
                        .number(554)
                        .city("Jaboatão dos Guararapes")
                        .person(PersonUtil.newPersonsInList().get(0))
                        .build(),
                Address.builder()
                        .id(2l)
                        .relevanceLevel(AddressRelevanceLevel.MEDIUM)
                        .publicPlace("Rua Guanabara")
                        .zipCode("65056557")
                        .number(681)
                        .city("São Luís")
                        .person(PersonUtil.newPersonsInList().get(0))
                        .build(),
                Address.builder()
                        .id(3l)
                        .relevanceLevel(AddressRelevanceLevel.LOW)
                        .publicPlace("Vila Mário de Andrade")
                        .zipCode("66032355")
                        .number(862)
                        .city("Belém")
                        .person(PersonUtil.newPersonsInList().get(0))
                        .build(),
                Address.builder()
                        .id(4l)
                        .relevanceLevel(AddressRelevanceLevel.HIGH)
                        .publicPlace("Rua Francelino Silveira Ramos")
                        .zipCode("95070550")
                        .number(971)
                        .city("Caxias do Sul")
                        .person(PersonUtil.newPersonsInList().get(1))
                        .build(),
                Address.builder()
                        .id(5l)
                        .relevanceLevel(AddressRelevanceLevel.HIGH)
                        .publicPlace("Rua Vanderléia Teotonia de Faria")
                        .zipCode("88161388")
                        .number(34)
                        .city("Biguaçu")
                        .person(PersonUtil.newPersonsInList().get(2))
                        .build(),
                Address.builder()
                        .id(6l)
                        .relevanceLevel(AddressRelevanceLevel.MEDIUM)
                        .publicPlace("Avenida Fernando Pacheco Jordão")
                        .zipCode("08121510")
                        .number(1764)
                        .city("São Paulo")
                        .person(PersonUtil.newPersonsInList().get(2))
                        .build()
        );
    }

    public static Page<Address> newAddressesInPage() {
        return new PageImpl<Address>(newAddresses());
    }

}
