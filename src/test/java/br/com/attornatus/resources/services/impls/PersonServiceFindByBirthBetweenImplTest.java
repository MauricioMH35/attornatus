package br.com.attornatus.resources.services.impls;

import br.com.attornatus.exceptions.NotFoundException;
import br.com.attornatus.models.entities.Person;
import br.com.attornatus.models.repositories.PersonRepository;
import br.com.attornatus.models.utils.PersonUtil;
import br.com.attornatus.resources.services.utils.BirthServiceUtil;
import br.com.attornatus.resources.services.utils.PageableServiceUtil;
import br.com.attornatus.utils.LocalDateUtility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Clock;
import java.time.LocalDate;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class PersonServiceFindByBirthBetweenImplTest {

    @InjectMocks private PersonServiceFindByBirthBetweenImpl underTest;
    @MockBean private PersonRepository repository;
    @MockBean private Clock clock;

    private LocalDateUtility localDateUtility;
    private BirthServiceUtil birthServiceUtil;
    private PageableServiceUtil pageableServiceUtil;

    private String startBirthString;
    private String endBirthString;
    private LocalDate startBirth;
    private LocalDate endBirth;
    private Page<Person> founded;
    private Map<String, String> pageParams;
    private Pageable pageable;

    @BeforeEach void setUp() {
        localDateUtility = new LocalDateUtility(clock);
        birthServiceUtil = new BirthServiceUtil(clock, localDateUtility);
        pageableServiceUtil = new PageableServiceUtil();
        underTest = new PersonServiceFindByBirthBetweenImpl(birthServiceUtil, pageableServiceUtil, repository);

        startBirthString = "01-08-1990";
        endBirthString = "30-10-1990";
        startBirth = LocalDate.of(1990, 8, 01);
        endBirth = LocalDate.of(1990, 10, 30);
        founded = PersonUtil.newSingleEntityInPage();
        pageParams = Map.of("page", "0", "size", "10");
        pageable = pageableServiceUtil
                .targetSort("name")
                .sortType(PageableServiceUtil.SortType.ASCENDING)
                .buildPageable(pageParams);
    }

    @Test
    @DisplayName("When Find By Birth Between Returns Persons The Same Birth")
    void whenSuccessful() {
        when(repository.findByBirthBetween(startBirth, endBirth, pageable)).thenReturn(founded);

        Page<Person> expected = founded;
        Page<Person> actual = underTest.apply(startBirthString, endBirthString, pageParams);
        verify(repository, times(1)).findByBirthBetween(startBirth, endBirth, pageable);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("When Find By Birth Between Not Found Returns Not Found Exception")
    void whenNotFound() {
        final String exceptionMsg = "Não foram encontradas pessoas entre as datas de nascimento informadas.";
        when(repository.findByBirthBetween(startBirth, endBirth, pageable)).thenReturn(Page.empty());

        assertThatThrownBy(() -> underTest.apply(startBirthString, endBirthString, pageParams))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining(exceptionMsg);
    }

}
