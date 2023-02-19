package br.com.attornatus.resources.services.impls;

import br.com.attornatus.models.entities.Person;
import br.com.attornatus.models.repositories.PersonRepository;
import br.com.attornatus.resources.services.PersonServiceFindById;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PersonServiceFindByIdImpl implements PersonServiceFindById {

    private final Logger log = LoggerFactory.getLogger(PersonServiceFindByIdImpl.class);
    private final PersonRepository repository;

    @Override
    public Person apply(Long id) {
        return null;
    }

}
