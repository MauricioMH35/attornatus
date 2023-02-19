package br.com.attornatus.resources.services.impls;

import br.com.attornatus.models.repositories.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class PersonServiceFindByIdImplTest {

    @InjectMocks private PersonServiceFindByIdImpl underTest;
    @MockBean private PersonRepository repository;

    @BeforeEach void setUp() {
        underTest = new PersonServiceFindByIdImpl(repository);
    }

    @Test
    void appy() {

    }

    @Test
    @DisplayName("When Find By Id Person With Empty Id Should Throw An IllegalArgumentException")
    void whenFindByIdEmptyId() {
        final Long id = null;
        final String exceptionMessage = "Para realizar essa operação o id da pessoa deve ser informado.";
        assertThatThrownBy(() -> underTest.apply(id))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(exceptionMessage);
    }

    @Test
    @DisplayName("When Find By Id Person With Id Less Than One Should Throw An IllegalArgumentException")
    void whenFindByIdLessThanOne() {
        final Long id = -1l;
        final String exceptionMessage = "Para realizar essa operação o id da pessoa deve ser maior do que um.";
        assertThatThrownBy(() -> underTest.apply(id))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(exceptionMessage);
    }

}