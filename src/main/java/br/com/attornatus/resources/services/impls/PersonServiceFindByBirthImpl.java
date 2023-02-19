package br.com.attornatus.resources.services.impls;

import br.com.attornatus.exceptions.NotFoundException;
import br.com.attornatus.models.entities.Person;
import br.com.attornatus.models.repositories.PersonRepository;
import br.com.attornatus.resources.services.PersonServiceFindByBirth;
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
public class PersonServiceFindByBirthImpl implements PersonServiceFindByBirth {

    private final Logger log = LoggerFactory.getLogger(PersonServiceFindByBirthImpl.class);
    private final BirthServiceUtil birthServiceUtil;
    private final PageableServiceUtil pageableServiceUtil;
    private final PersonRepository repository;

    @Override
    public Page<Person> apply(String birth, Map<String, String> pageParams) {
        LocalDate birthDate = birthServiceUtil.parseBirth(birth);
        Pageable pageable = pageableServiceUtil
                .targetSort("name")
                .sortType(PageableServiceUtil.SortType.ASCENDING)
                .buildPageable(pageParams);

        Page<Person> founded = repository.findByBirth(birthDate, pageable);
        throwExceptionNotFound(founded);
        return founded;
    }

    private void throwExceptionNotFound(Page<Person> found) {
        if (found.isEmpty()) {
            throw new NotFoundException("Não foram encontradas pessoas como a data de nascimento informada.");
        }
    }

}
