package br.com.attornatus.resources.services.impls;

import br.com.attornatus.exceptions.NotFoundException;
import br.com.attornatus.models.entities.Person;
import br.com.attornatus.models.repositories.PersonRepository;
import br.com.attornatus.resources.services.PersonServiceFindByNameContains;
import br.com.attornatus.resources.services.utils.PageableServiceUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class PersonServiceFindByNameContainsImpl implements PersonServiceFindByNameContains {

    private final Logger log = LoggerFactory.getLogger(PersonServiceFindByNameContainsImpl.class);
    private final PageableServiceUtil pageableServiceUtil;
    private final PersonRepository repository;

    @Override
    public Page<Person> apply(String name, Map<String, String> pageParams) {
        Pageable pageable = pageableServiceUtil
                .targetSort("name")
                .sortType(PageableServiceUtil.SortType.ASCENDING)
                .buildPageable(pageParams);

        Page<Person> founded = repository.findByNameContains(name, pageable);
        throwsExceptionWhenNotFoundByName(founded, name);
        return founded;
    }

    private void throwsExceptionWhenNotFoundByName(Page<Person> founded, String name) {
        if (founded.isEmpty()) {
            log.warn("Uma tentativa de consulta para listar as pessoas que contenha o nome (" + name + ") cadastradas " +
                    "no sistema foi realizada, mas não foram encontrados registros.");

            throw new NotFoundException("Não foram encontadas pessoas cadastradas com o nome (" + name + ") informado.");
        }
    }

}
