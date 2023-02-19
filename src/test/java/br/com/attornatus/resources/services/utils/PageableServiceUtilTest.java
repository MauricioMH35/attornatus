package br.com.attornatus.resources.services.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
class PageableServiceUtilTest {

    @InjectMocks private PageableServiceUtil underTest;

    @BeforeEach void setUp() {
        underTest = new PageableServiceUtil();
    }

    @Test
    @DisplayName("When Page And Size Should Return Successful Pageable")
    void whenPageAndSizeReturnSuccessfulPageable() {
        final Map<String, String> pageParams = Map.of("page", "0", "size", "10");

        final Integer page = 0;
        final Integer size = 10;
        final String targetSort = "name";
        final Sort sort = Sort.by(targetSort).ascending();

        Pageable expected = PageRequest.of(page, size, sort);
        Pageable actual = underTest
                .targetSort(targetSort)
                .sortType(PageableServiceUtil.SortType.ASCENDING)
                .buildPageable(pageParams);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("When Not Found Page And Size Should Return Illegal Argument Exception")
    void whenNotFoundPageAndSize() {
        final Map<String, String> pageParams = Map.of("pageNoExist", "0", "sizeNoExist", "10");
        final String throwMsg = "Não foi possivel localizar a `página` e `quantidade de elementos na `página`.";

        assertThatThrownBy(() -> underTest.buildPageable(pageParams))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(throwMsg);
    }

    @Test
    @DisplayName("When Page And Size Are Blank Should Return Illegal Argument Exception")
    void whenPageAndSizeAreBlank() {
        final Map<String, String> pageParams = Map.of("page", "", "size", "");
        final String throwMsg = "Não foi possivel encontrar os valores da `page` e `size`.";

        assertThatThrownBy(() -> underTest.buildPageable(pageParams))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(throwMsg);
    }

    @Test
    @DisplayName("When Page And Size Not Numeric Should Return Illegal Argument Exception")
    void whenPageAndSizeNotNumeric() {
        final Map<String, String> pageParams = Map.of("page", "zero", "size", "ten");
        final String throwMsg = "Não é possivel determinar se os parâmetros `page` e `size` como valores numéricos.";

        assertThatThrownBy(() -> underTest.buildPageable(pageParams))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(throwMsg);
    }

    @Test
    @DisplayName("When Page And Size Less Than Zero Should Return Illegal Argument Exception")
    void whenPageAndSizeLessThanZero() {
        final Map<String, String> pageParams = Map.of("page", "-1", "size", "-10");
        final String throwMsg = "Não é possivel realizar a operação com os parâmetros `page` e `size` sendo menores " +
                "do que zero.";

        assertThatThrownBy(() -> underTest.buildPageable(pageParams))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(throwMsg);
    }

}