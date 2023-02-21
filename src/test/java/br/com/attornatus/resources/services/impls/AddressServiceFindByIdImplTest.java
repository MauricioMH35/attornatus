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
class AddressServiceFindByIdImplTest {

    @InjectMocks private AddressServiceFindByIdImpl underTest;
    @MockBean private IdServiceUtil idServiceUtil;
    @MockBean private AddressRepository repository;

    private Long id;
    private Address founded;

    @BeforeEach void setUp() {
        underTest = new AddressServiceFindByIdImpl(idServiceUtil, repository);

        founded = AddressUtil.newAddressWithId();
    }

    @Test
    @DisplayName("When Find By Id Return Successful")
    void whenFindByIdReturnSuccessful() {
        when(repository.findById(id)).thenReturn(Optional.of(founded));

        Address expected = founded;
        Address actual = underTest.apply(id);

        verify(repository, times(1)).findById(id);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("When Not Found By Id Returning Not Found Exception")
    void whenNotFound() {
        final String exceptionMessage = "Não foi possivel encontrar o endereço.";
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> underTest.apply(id))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining(exceptionMessage);
    }

}