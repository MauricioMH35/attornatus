package br.com.attornatus.resources.services.impls;

import br.com.attornatus.models.entities.Person;
import br.com.attornatus.models.repositories.PersonRepository;
import br.com.attornatus.resources.services.PersonServiceFindByBirthBetween;
import br.com.attornatus.resources.services.utils.PageableServiceUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class PersonServiceFindByBirthBetweenImpl implements PersonServiceFindByBirthBetween {

    private final Logger log = LoggerFactory.getLogger(PersonServiceFindByBirthBetweenImpl.class);
    private final PageableServiceUtil pageableServiceUtil;
    private final PersonRepository repository;

    @Override
    public Page<Person> apply(Map<String, String> betweenParams, Map<String, String> pageParams) {
        return null;
    }

}
