package br.com.attornatus.resources.services.impls;

import br.com.attornatus.exceptions.NotFoundException;
import br.com.attornatus.models.entities.Person;
import br.com.attornatus.models.repositories.PersonRepository;
import br.com.attornatus.models.utils.PersonUtil;
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
class PersonServiceFindAllImplTest {

    @InjectMocks private PersonServiceFindAllImpl underTest;
    @MockBean private PersonRepository repository;

    @BeforeEach void setUp() {
        underTest = new PersonServiceFindAllImpl(repository);
    }

    @Test
    @DisplayName("When Find All Persons Should Return Page of Persons Founded")
    void whenSuccessReturnPageOfPersons() {
        final Map<String, String> pageParams = Map.of("page", "0", "size", "10");
        final Integer page = Integer.parseInt(pageParams.get("page"));
        final Integer size = Integer.parseInt(pageParams.get("size"));

        assertEquals(0, page);
        assertEquals(10, size);
        final Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());

        final Page<Person> founded = PersonUtil.newPersonsInPage();
        when(repository.findAll(pageable)).thenReturn(founded);

        Page<Person> expected = PersonUtil.newPersonsInPage();
        Page<Person> actual = underTest.apply(pageParams);
        verify(repository, times(1)).findAll(pageable);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("When Find All Persons With Page Fields Not Found Should Throw An IllegalArgumentException")
    void whenPageFieldsAreNotFound() {
        final Map<String, String> pageParams = Map.of("pageNoExist", "0", "sizeNoExist", "10");
        final String exceptionMessage = "Não é possivel realizar a operação, os parâmetros informados não são válidos.";

        assertThatThrownBy(() -> underTest.apply(pageParams))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(exceptionMessage);
    }

    @Test
    @DisplayName("When Find All Persons With Page Fields Are Blank Should Throw An IllegalArgumentException")
    void whenPageFieldsAreBlank() {
        final Map<String, String> pageParams = Map.of("page", "", "size", "");
        final String exceptionMessage = "Não é possivel realizar a operação, os parâmetros informados não são válidos.";

        assertThatThrownBy(() -> underTest.apply(pageParams))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(exceptionMessage);
    }

    @Test
    @DisplayName("When Find All Persons With Page Fields Are Not Numerics Should Throw An IllegalArgumentException")
    void whenPageFieldAreNotNumerics() {
        final Map<String, String> pageParams = Map.of("page", "zero", "size", "then");
        final String exceptionMessage = "Não é possivel realizar a operação, os parâmetros `page` e `size` não " +
                "são valores numéricos.";

        Boolean expected = false;
        Boolean actual = checkMapParamPageAreNumerics(pageParams);

        assertEquals(expected, actual);

        assertThatThrownBy(() -> underTest.apply(pageParams))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(exceptionMessage);
    }

    @Test
    @DisplayName("When Find All Persons With Page Fields Less Than Zero Should Throw An IllegalArgumentException")
    void whenPageFieldAreLessThanZero() {
        final Map<String, String> pageParams = Map.of("page", "-10", "size", "-1");
        final String exceptionMessage = "Não é possivel realizar a operação com os parâmetros informados sendo " +
                "menores do que zero.";

        assertThatThrownBy(() -> underTest.apply(pageParams))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(exceptionMessage);
    }

    @Test
    @DisplayName("When Find All Persons With Not Found Should Throw An NotFoundException")
    void whenNotFoundPersons() {
        final Map<String, String> pageParams = Map.of("page", "0", "size", "10");
        final Pageable pageable = PageRequest.of(0, 10, Sort.by("name").ascending());
        final String exceptionMessage = "Não foram encontadas pessoas cadastradas.";

        when(repository.findAll(pageable)).thenReturn(Page.empty());

        assertThatThrownBy(() -> underTest.apply(pageParams))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining(exceptionMessage);
    }

    private boolean checkMapParamPageAreNumerics(Map<String, String> pageParams) {
        boolean isPageNumeric = checkFieldIsNumeric(pageParams.get("page"));
        boolean isSizeNumeric = checkFieldIsNumeric(pageParams.get("size"));

        return isPageNumeric && isSizeNumeric;
    }

    private boolean checkFieldIsNumeric(String field) {
        try {
            Integer.parseInt(field);
            return true;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }

}