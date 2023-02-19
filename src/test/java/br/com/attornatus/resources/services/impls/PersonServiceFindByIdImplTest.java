package br.com.attornatus.resources.services.impls;

import br.com.attornatus.models.entities.Person;
import br.com.attornatus.models.repositories.PersonRepository;
import br.com.attornatus.models.utils.PersonUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class PersonServiceFindByIdImplTest {

    @InjectMocks private PersonServiceFindByIdImpl underTest;
    @MockBean private PersonRepository repository;

    @BeforeEach void setUp() {
        underTest = new PersonServiceFindByIdImpl(repository);
    }

    @Test
    @DisplayName("When Find By Id Person Should Return Person Founded")
    void whenFindByIdReturnPersonFounded() {
        final Long id = 1l;
        final Person founded = PersonUtil.newEntity();
        when(repository.findById(id)).thenReturn(Optional.of(founded));

        Person expected = founded;
        Person actual = underTest.apply(id);
        verify(repository, times(1)).findById(id);
        assertEquals(expected, actual);
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