package br.com.attornatus.resources.hateoas.models;

import br.com.attornatus.models.entities.Address;
import br.com.attornatus.models.entities.Person;
import br.com.attornatus.models.enums.AddressRelevanceLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;

@RequiredArgsConstructor
@Builder
@Getter
public class AddressModel extends RepresentationModel<AddressModel> implements Serializable {

    public static final long serialVersionUID = 1l;

    @NonNull private Address content;

    public static AddressModel toModel(Address entity) {
        return new AddressModel(entity);
    }

}
