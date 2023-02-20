package br.com.attornatus.resources.services.impls;

import br.com.attornatus.models.entities.Person;
import br.com.attornatus.models.repositories.PersonRepository;
import br.com.attornatus.resources.services.PersonServiceUpdateById;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PersonServiceUpdateByIdImpl implements PersonServiceUpdateById {

    private final Logger log = LoggerFactory.getLogger(PersonServiceUpdateByIdImpl.class);
    private final PersonRepository repository;

    @Override
    public Person apply(Long id, Person person) {
        return null;
    }

}
