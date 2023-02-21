package br.com.attornatus.resources.services.impls;

import br.com.attornatus.exceptions.InternalServerErrorException;
import br.com.attornatus.exceptions.NotFoundException;
import br.com.attornatus.models.entities.Address;
import br.com.attornatus.models.enums.AddressRelevanceLevel;
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
class AddressServiceUpdateByIdImplTest {

    @InjectMocks private AddressServiceUpdateByIdImpl underTest;
    @MockBean private AddressRepository repository;
    @MockBean private IdServiceUtil idServiceUtil;

    private Long id;
    private Address update;
    private Address updated;
    private Address founded;

    @BeforeEach void setUp() {
        underTest = new AddressServiceUpdateByIdImpl(idServiceUtil, repository);

        id = 1l;
        update = AddressUtil.newAddressByRelevanceLevel(AddressRelevanceLevel.HIGH);
        updated = AddressUtil.newAddressByRelevanceLevelWithId(AddressRelevanceLevel.HIGH);
        founded = AddressUtil.newAddressByRelevanceLevelWithId(AddressRelevanceLevel.MEDIUM);
    }

    @Test
    @DisplayName("When Update By Id Return Successful")
    void whenUpdateByIdSuccessful() {
        when(repository.findById(id)).thenReturn(Optional.of(founded));
        when(repository.save(updated)).thenReturn(updated);

        Address expected = updated;
        Address actual = underTest.apply(id, update);
        verify(repository, times(1)).findById(id);
        verify(repository, times(1)).save(updated);

        assertNotEquals(founded, actual);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("When Not Found To Update By Id Returning Not Found Exception")
    void whenNotFound() {
        final String exceptionMessage = "Não foi possivel encontrar o endereço informado pelo id.";
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> underTest.apply(id, update))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining(exceptionMessage);
    }

    @Test
    @DisplayName("When Not Updated To Update By Id It Return Internal Server Error")
    void whenNotUpdate() {
        final String exceptionMessage = "Não foi possivel atualizar as informações do endereço.";
        when(repository.findById(id)).thenReturn(Optional.of(founded));
        when(repository.save(updated)).thenReturn(null);

        assertThatThrownBy(() -> underTest.apply(id, update))
                .isInstanceOf(InternalServerErrorException.class)
                .hasMessageContaining(exceptionMessage);
    }

}