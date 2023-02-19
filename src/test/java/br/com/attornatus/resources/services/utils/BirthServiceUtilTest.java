package br.com.attornatus.resources.services.utils;

import br.com.attornatus.utils.LocalDateUtility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Clock;
import java.time.LocalDate;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class BirthServiceUtilTest {

    @InjectMocks private BirthServiceUtil underTest;
    @MockBean private Clock clock;
    private LocalDateUtility localDateUtility;

    @BeforeEach void setUp() {
        localDateUtility = new LocalDateUtility(clock);
        underTest = new BirthServiceUtil(clock, localDateUtility);
    }

    @Test
    @DisplayName("When Parse Birth By LocalDate Return Successful LocalDate")
    void whenParseBirthReturnSuccessfulLocalDate() {
        final String birthString = "21-05-1992";

        LocalDate expected = LocalDate.of(1992, 05, 21);
        LocalDate actual = underTest.parseBirth(birthString);

        System.out.println("expected: " + expected);
        System.out.println("actual: " + actual);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("When Empty Birth Should Return Illegal Argument Exception")
    void whenBirthEmpty() {
        final String birth = "";
        final String throwMsg = "A data de nascimento não foi informada.";

        assertThatThrownBy(() -> underTest.parseBirth(birth))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(throwMsg);
    }

    @Test
    @DisplayName("When Birth Not Formatted Of Date Should Return Illegal Argument Exception")
    void whenBirthNotFormattedOfDate() {
        final String birth = "23 de outubro de 1990";
        final String throwMsg = "A data de nascimento informada é de um formato não conhecido pela applicação.";

        assertThatThrownBy(() -> underTest.parseBirth(birth))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(throwMsg);
    }

}