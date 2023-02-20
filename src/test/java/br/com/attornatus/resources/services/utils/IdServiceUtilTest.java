package br.com.attornatus.resources.services.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
class IdServiceUtilTest {

    @InjectMocks private IdServiceUtil underTest;

    @BeforeEach void setUp() {
        underTest = new IdServiceUtil();
    }

    @Test
    @DisplayName("When Id Is Valid It Must Return True")
    void whenValidReturnTrue() {
        Boolean expected = true;
        Boolean actual = underTest.validIdentity(1l);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("When The Given Id Is Empty It Should Return Illegal Argument Exception")
    void whenEmptyId() {
        final Long id = null;
        final String exceptionMessage = "O id (" + id + ") informado não pode ser nulo.";

        assertThatThrownBy(() -> underTest.validIdentity(id))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(exceptionMessage);
    }

    @Test
    @DisplayName("When Id Is Less Than One It Should An Illegal Argument Exception")
    void whenIdIsLessThanOne() {
        final Long id = -1l;
        final String exceptionMessage = "O id (" + id + ") informado não pode ser menor do que zero.";

        assertThatThrownBy(() -> underTest.validIdentity(id))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(exceptionMessage);
    }

}