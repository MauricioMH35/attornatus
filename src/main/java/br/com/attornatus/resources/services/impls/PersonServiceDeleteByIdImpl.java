package br.com.attornatus.resources.services.impls;

import br.com.attornatus.exceptions.NotFoundException;
import br.com.attornatus.models.entities.Person;
import br.com.attornatus.models.repositories.PersonRepository;
import br.com.attornatus.resources.services.PersonServiceDeleteById;
import br.com.attornatus.resources.services.utils.IdServiceUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PersonServiceDeleteByIdImpl implements PersonServiceDeleteById {

    private final Logger log = LoggerFactory.getLogger(PersonServiceDeleteByIdImpl.class);
    private final IdServiceUtil idServiceUtil;
    private final PersonRepository repository;

    @Override
    public Boolean apply(Long id) {
        findById(id);
        return delete(id);
    }

    private Boolean delete(Long id) {
        repository.deleteById(id);
        return true;
    }

    private void findById(Long id) {
        idServiceUtil.validIdentity(id);
        Optional<Person> founded = repository.findById(id);
        throwExceptionNotFoundById(founded);
    }

    private void throwExceptionNotFoundById(Optional<Person> founded) {
        if (founded.isEmpty()) {
            throw new NotFoundException("Não foi possivel encontrar a pessoa indicada pelo id informado.");
        }
    }

}
