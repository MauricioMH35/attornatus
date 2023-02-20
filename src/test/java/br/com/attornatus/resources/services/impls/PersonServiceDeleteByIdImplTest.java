package br.com.attornatus.resources.services.impls;

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

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class PersonServiceDeleteByIdImplTest {

    @InjectMocks private PersonServiceDeleteByIdImpl underTest;
    @MockBean private IdServiceUtil idServiceUtil;
    @MockBean private PersonRepository repository;

    private Long id;
    private Person founded;

    @BeforeEach void setUp() {
        underTest= new PersonServiceDeleteByIdImpl(idServiceUtil, repository);

        id = 1l;
        founded = PersonUtil.newEntity();
    }

    @Test
    @DisplayName("When Delete By id Returns Successful")
    void whenDeleteByIdSuccessful() {
        when(repository.findById(id)).thenReturn(Optional.of(founded));

        underTest.apply(id);
        verify(repository, times(1)).deleteById(id);
    }

    @Test
    @DisplayName("When Not Found By Id Return Not Found Exception")
    void whenNotFoundedById() {
        final String exceptionMsg = "Não foi possivel encontrar a pessoa indicada pelo id informado.";
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> underTest.apply(id))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining(exceptionMsg);
    }

}