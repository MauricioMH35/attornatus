package br.com.attornatus.resources.services.impls;

import br.com.attornatus.exceptions.NotFoundException;
import br.com.attornatus.models.entities.Address;
import br.com.attornatus.models.repositories.AddressRepository;
import br.com.attornatus.models.utils.AddressUtil;
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
class AddressServiceDeleteByIdImplTest {

    @InjectMocks private AddressServiceDeleteByIdImpl underTest;
    @MockBean private AddressRepository repository;
    @MockBean private IdServiceUtil idServiceUtil;

    private Long id;
    private Address founded;

    @BeforeEach void setUp() {
        underTest = new AddressServiceDeleteByIdImpl(idServiceUtil, repository);

        id = 1l;
        founded = AddressUtil.newAddressWithId();
    }

    @Test
    @DisplayName("When Delete By Id Return Successful")
    void whenDeleteByIdSuccessful() {
        when(repository.findById(id)).thenReturn(Optional.of(founded));

        Boolean expected = true;
        Boolean actual = underTest.apply(id);

        verify(repository, times(1)).findById(id);
        verify(repository, times(1)).deleteById(id);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("When Not Found To Delete By Id Returning Not Found Exception")
    void whenNotFound() {
        final String exceptionMessage = "Não foi possivel encontrar o endereço.";
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> underTest.apply(id))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining(exceptionMessage);
    }
}