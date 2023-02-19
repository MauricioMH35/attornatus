package br.com.attornatus.resources.services.impls;

import br.com.attornatus.models.repositories.PersonRepository;
import br.com.attornatus.resources.services.PersonServiceDeleteById;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PersonServiceDeleteByIdImpl implements PersonServiceDeleteById {

    private final Logger log = LoggerFactory.getLogger(PersonServiceDeleteByIdImpl.class);
    private final PersonRepository repository;

    @Override
    public Boolean apply(Long id) {
        return null;
    }

}
