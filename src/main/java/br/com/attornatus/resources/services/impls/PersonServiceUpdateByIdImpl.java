package br.com.attornatus.resources.services.impls;

import br.com.attornatus.exceptions.InternalServerErrorException;
import br.com.attornatus.exceptions.NotFoundException;
import br.com.attornatus.models.entities.Person;
import br.com.attornatus.models.repositories.PersonRepository;
import br.com.attornatus.resources.services.PersonServiceUpdateById;
import br.com.attornatus.resources.services.utils.IdServiceUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PersonServiceUpdateByIdImpl implements PersonServiceUpdateById {

    private final Logger log = LoggerFactory.getLogger(PersonServiceUpdateByIdImpl.class);
    private final IdServiceUtil idServiceUtil;
    private final PersonRepository repository;

    @Override
    public Person apply(Long id, Person person) {
        Person founded = findByid(id);
        Person updated = update(founded, person);
        throwExceptionNotUpdated(updated);
        return updated;
    }

    private Person update(Person founded, Person update) {
        Person updatedFields = updateFields(founded, update);
        return repository.save(updatedFields);
    }

    private Person updateFields(Person founded, Person update) {
        Long id = founded.getId();

        String name = updateName(
                founded.getName(),
                update.getName());

        LocalDate birth = updateBirth(
                founded.getBirth(),
                update.getBirth());

        return Person.builder()
                .id(id)
                .name(name)
                .birth(birth)
                .build();
    }

    private String updateName(String founded, String updated) {
        return founded.equals(updated) ? founded : updated;
    }

    private LocalDate updateBirth(LocalDate founded, LocalDate updated) {
        return founded.equals(updated) ? founded : updated;
    }

    private Person findByid(Long id) {
        idServiceUtil.validIdentity(id);
        Optional<Person> founded = repository.findById(id);
        throwExceptionNotFoundById(founded);
        return founded.get();
    }

    private void throwExceptionNotFoundById(Optional<Person> founded) {
        if (founded.isEmpty()) {
            throw new NotFoundException("Não foi possivel encontrar a pessoa indicada pelo id informado.");
        }
    }

    private void throwExceptionNotUpdated(Person updated) {
        if (updated == null) {
            throw new InternalServerErrorException("Não foi possivel encontrar a pessoa atualizada.");
        }
    }

}
