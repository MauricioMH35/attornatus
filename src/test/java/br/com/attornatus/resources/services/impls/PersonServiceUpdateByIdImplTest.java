package br.com.attornatus.resources.services.impls;

import br.com.attornatus.exceptions.InternalServerErrorException;
import br.com.attornatus.exceptions.NotFoundException;
import br.com.attornatus.models.entities.Person;
import br.com.attornatus.models.repositories.PersonRepository;
import br.com.attornatus.models.utils.PersonUtil;
import br.com.attornatus.resources.services.utils.IdServiceUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class PersonServiceUpdateByIdImplTest {

    @InjectMocks private PersonServiceUpdateByIdImpl underTest;
    @MockBean private IdServiceUtil idServiceUtil;
    @MockBean private PersonRepository repository;

    private Long id;
    private LocalDate newBrith;
    private Person founded;
    private Person update;
    private Person updated;

    @BeforeEach void setUp() {
        underTest = new PersonServiceUpdateByIdImpl(idServiceUtil, repository);

        id = 1l;
        newBrith = LocalDate.of(1992, 05, 21);
        founded = PersonUtil.newEntity();
        update = PersonUtil.newEntityByBirth(newBrith);
        updated = PersonUtil.newEntityByBirthWithId(newBrith);
    }

    @Test
    @DisplayName("When Updated By Id Successfully")
    void whenUpdateSuccessful() {
        when(repository.findById(id)).thenReturn(Optional.of(founded));
        when(repository.save(updated)).thenReturn(updated);

        Person expected = PersonUtil.newEntityByBirthWithId(newBrith);
        Person actual = underTest.apply(id, update);
        verify(repository, times(1)).findById(id);
        verify(repository, times(1)).save(updated);

        assertNotEquals(founded, update);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("When Not Found By Id Return Not Found Exception")
    void whenNotFoundedById() {
        final String exceptionMsg = "Não foi possivel encontrar a pessoa indicada pelo id informado.";
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> underTest.apply(id, update))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining(exceptionMsg);
    }

    @Test
    @DisplayName("When Update By Id But Unable To Return Updated Person Resulting Internal Server Error")
    void whenNotUpdated() {
        final String exceptionMsg = "Não foi possivel encontrar a pessoa atualizada.";
        when(repository.findById(id)).thenReturn(Optional.of(founded));
        when(repository.save(updated)).thenReturn(null);

        assertThatThrownBy(() -> underTest.apply(id, update))
                .isInstanceOf(InternalServerErrorException.class)
                .hasMessageContaining(exceptionMsg);
    }

}
