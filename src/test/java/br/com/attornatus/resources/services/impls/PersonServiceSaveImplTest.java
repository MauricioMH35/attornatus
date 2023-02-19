package br.com.attornatus.resources.services.impls;

import br.com.attornatus.exceptions.InternalServerErrorException;
import br.com.attornatus.models.entities.Person;
import br.com.attornatus.models.repositories.PersonRepository;
import br.com.attornatus.models.utils.PersonUtil;
import br.com.attornatus.utils.LocalDateUtility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class PersonServiceSaveImplTest {

    @InjectMocks private PersonServiceSaveImpl underTest;
    @MockBean private PersonRepository repository;
    @MockBean private LocalDateUtility localDateUtil;

    @BeforeEach
    void setUp() {
        underTest = new PersonServiceSaveImpl(localDateUtil, repository);
    }

    @Test
    @DisplayName("When Save Person Should Return The Saved in Database")
    void whenSaveSuccessful() {
        Person save = PersonUtil.newFormEntity();
        Person saved = PersonUtil.newEntity();
        when(repository.save(save)).thenReturn(saved);

        Person expected = saved;
        Person actual = underTest.apply(save);

        verify(repository, times(1)).save(save);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("When Save Person With Empty Fileds Should Throw An IllegalArgumentException")
    void whenSaveEmptyFields() {
        Person personEmptyFields = Person.builder().build();
        assertThatThrownBy(() -> underTest.apply(personEmptyFields))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Para realizar essa operação as informações da pessoa devem ser passadas.");
    }

    @Test
    @DisplayName("When Save Person But Does Not Return Saved In Database Must Return An InternalServerErrorException")
    void whenNotToSave() {
        Person save = PersonUtil.newFormEntity();
        when(repository.save(save)).thenReturn(null);
        assertThatThrownBy(() -> underTest.apply(save))
                .isInstanceOf(InternalServerErrorException.class)
                .hasMessageContaining("Não foi possivel completar o cadastro da pessoa devido ao erro interno.");
    }

}