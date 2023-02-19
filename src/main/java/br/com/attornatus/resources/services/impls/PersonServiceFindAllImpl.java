package br.com.attornatus.resources.services.impls;

import br.com.attornatus.exceptions.NotFoundException;
import br.com.attornatus.models.entities.Person;
import br.com.attornatus.models.repositories.PersonRepository;
import br.com.attornatus.resources.services.PersonServiceFindAll;
import br.com.attornatus.resources.services.utils.PageableServiceUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class PersonServiceFindAllImpl implements PersonServiceFindAll {

    private final Logger log = LoggerFactory.getLogger(PersonServiceFindAllImpl.class);
    private final PageableServiceUtil pageableServiceUtil;
    private final PersonRepository repository;

    @Override
    public Page<Person> apply(Map<String, String> pageParams) {
        Pageable pageable = pageableServiceUtil
                .targetSort("name")
                .sortType(PageableServiceUtil.SortType.ASCENDING)
                .buildPageable(pageParams);

        Page<Person> founded = repository.findAll(pageable);
        throwsExceptionWhenNotFound(founded);
        return founded;
    }

    private void throwsExceptionWhenNotFound(Page<Person> founded) {
        if (founded.isEmpty()) {
            log.warn("Uma tentativa de consulta para listar as pessoas cadastradas no sistema foi realizada, mas não " +
                    "foram encontrados registros.");

            throw new NotFoundException("Não foram encontadas pessoas cadastradas.");
        }
    }

}
