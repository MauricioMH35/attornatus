package br.com.attornatus.resources.services.impls;

import br.com.attornatus.exceptions.NotFoundException;
import br.com.attornatus.models.entities.Person;
import br.com.attornatus.models.repositories.PersonRepository;
import br.com.attornatus.models.utils.PersonUtil;
import br.com.attornatus.resources.services.utils.BirthServiceUtil;
import br.com.attornatus.resources.services.utils.PageableServiceUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class PersonServiceFindByBirthImplTest {

    @InjectMocks private PersonServiceFindByBirthImpl underTest;
    @MockBean private PersonRepository repository;
    @MockBean private BirthServiceUtil birthServiceUtil;
    private PageableServiceUtil pageableServiceUtil;

    @BeforeEach void setUp() {
        pageableServiceUtil = new PageableServiceUtil();
        underTest = new PersonServiceFindByBirthImpl(birthServiceUtil, pageableServiceUtil, repository);
    }

    @Test
    @DisplayName("When Find By Birth Person Should Return Page Persons Founded")
    void whenFindByBirthReturnSuccessful() {
        System.out.println(repository.findAll().toArray().toString());
        final String birthString = "23-08-1990";
        final Map<String, String> pageParams = Map.of("page", "0", "size", "10");

        final LocalDate birth = birthServiceUtil.parseBirth(birthString);
        final Pageable pageable = pageableServiceUtil
                .targetSort("name")
                .sortType(PageableServiceUtil.SortType.ASCENDING)
                .buildPageable(pageParams);

        final Page<Person> founded = PersonUtil.newSingleEntityInPage();
        when(repository.findByBirth(birth, pageable)).thenReturn(founded);

        Page<Person> expected = founded;
        Page<Person> actual = underTest.apply(birthString, pageParams);

        verify(repository, times(1)).findByBirth(birth, pageable);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("When Find By Birth Persons But Not Found Should Return Not Found Exception")
    void whenFindByBirthButNotFound() {
        final String birthString = "23-08-1990";
        final Map<String, String> pageParams = Map.of("page", "0", "size", "10");
        final String exceptionMsg = "Não foram encontradas pessoas como a data de nascimento informada.";

        final LocalDate birth = birthServiceUtil.parseBirth(birthString);
        final Pageable pageable = pageableServiceUtil
                .targetSort("name")
                .sortType(PageableServiceUtil.SortType.ASCENDING)
                .buildPageable(pageParams);

        when(repository.findByBirth(birth, pageable)).thenReturn(Page.empty());

        assertThatThrownBy(() -> underTest.apply(birthString, pageParams))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining(exceptionMsg);
    }

}
