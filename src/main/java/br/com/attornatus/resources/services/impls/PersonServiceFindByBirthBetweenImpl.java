package br.com.attornatus.resources.services.impls;

import br.com.attornatus.exceptions.NotFoundException;
import br.com.attornatus.models.entities.Person;
import br.com.attornatus.models.repositories.PersonRepository;
import br.com.attornatus.resources.services.PersonServiceFindByBirthBetween;
import br.com.attornatus.resources.services.utils.BirthServiceUtil;
import br.com.attornatus.resources.services.utils.PageableServiceUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class PersonServiceFindByBirthBetweenImpl implements PersonServiceFindByBirthBetween {

    private final Logger log = LoggerFactory.getLogger(PersonServiceFindByBirthBetweenImpl.class);
    private final BirthServiceUtil birthServiceUtil;
    private final PageableServiceUtil pageableServiceUtil;
    private final PersonRepository repository;

    @Override
    public Page<Person> apply(String start,
                              String end,
                              Map<String, String> pageParams
    ) {
        LocalDate startBirth = birthServiceUtil.parseBirth(start);
        LocalDate endBirth = birthServiceUtil.parseBirth(end);
        Pageable pageable = pageableServiceUtil
                .targetSort("name")
                .sortType(PageableServiceUtil.SortType.ASCENDING)
                .buildPageable(pageParams);

        Page<Person> founded = repository
                .findByBirthBetween(
                        startBirth,
                        endBirth,
                        pageable
                );
        throwExceptionNotFound(founded);
        return founded;
    }

    private void throwExceptionNotFound(Page<Person> found) {
        if (found.isEmpty()) {
            throw new NotFoundException("Não foram encontradas pessoas entre as datas de nascimento informadas.");
        }
    }

}
