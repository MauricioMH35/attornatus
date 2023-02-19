package br.com.attornatus.resources.services.impls;

import br.com.attornatus.models.entities.Person;
import br.com.attornatus.models.repositories.PersonRepository;
import br.com.attornatus.resources.services.PersonServiceFindByNameContains;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class PersonServiceFindByNameContainsImpl implements PersonServiceFindByNameContains {

    private final Logger log = LoggerFactory.getLogger(PersonServiceFindByNameContainsImpl.class);
    private final PersonRepository repository;

    @Override
    public Page<Person> apply(String name, Map<String, String> pageParams) {
        return null;
    }

}
