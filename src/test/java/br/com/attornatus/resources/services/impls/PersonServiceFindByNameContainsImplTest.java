package br.com.attornatus.resources.services.impls;

import br.com.attornatus.exceptions.NotFoundException;
import br.com.attornatus.models.entities.Person;
import br.com.attornatus.models.repositories.PersonRepository;
import br.com.attornatus.models.utils.PersonUtil;
import br.com.attornatus.resources.services.utils.PageableServiceUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class PersonServiceFindByNameContainsImplTest {

    @InjectMocks private PersonServiceFindByNameContainsImpl underTest;
    @MockBean private PersonRepository repository;
    private PageableServiceUtil pageableServiceUtil;

    @BeforeEach void setUp() {
        pageableServiceUtil = new PageableServiceUtil();
        underTest = new PersonServiceFindByNameContainsImpl(pageableServiceUtil, repository);
    }

    @Test
    @DisplayName("When Find By Name Contains Persons Should Return Page of Persons Founded")
    void whenSuccessReturnPageOfPersons() {
        final Map<String, String> pageParams = Map.of("page", "0", "size", "10");
        final Integer page = Integer.parseInt(pageParams.get("page"));
        final Integer size = Integer.parseInt(pageParams.get("size"));

        assertEquals(0, page);
        assertEquals(10, size);
        final Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());

        final String name = "Flavia";
        final Page<Person> founded = PersonUtil.newPersonsInPage();
        when(repository.findByNameContains(name, pageable)).thenReturn(founded);

        Page<Person> expected = PersonUtil.newPersonsInPage();
        Page<Person> actual = underTest.apply(name, pageParams);
        verify(repository, times(1)).findByNameContains(name, pageable);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("When Find By Name Contains Persons With Not Found Should Throw An NotFoundException")
    void whenNotFoundPersons() {
        final Map<String, String> pageParams = Map.of("page", "0", "size", "10");
        final Pageable pageable = PageRequest.of(0, 10, Sort.by("name").ascending());
        final String name = "Flavia";
        final String exceptionMessage = "N??o foram encontadas pessoas cadastradas com o nome (" + name + ") informado.";

        when(repository.findByNameContains(name, pageable)).thenReturn(Page.empty());

        assertThatThrownBy(() -> underTest.apply(name, pageParams))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining(exceptionMessage);
    }

}
