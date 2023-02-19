package br.com.attornatus.resources.hateoas.models;

import br.com.attornatus.models.entities.Person;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.time.LocalDate;

@RequiredArgsConstructor
@Builder
@Getter
public class PersonModel extends RepresentationModel<PersonModel> implements Serializable {

    public static final long serialVerisonUID = 1l;

    @NonNull private Person content;

    public static PersonModel toModel(Person entity) {
        return new PersonModel(entity);
    }

}
