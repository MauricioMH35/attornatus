package br.com.attornatus.resources.services.impls;

import br.com.attornatus.exceptions.InternalServerErrorException;
import br.com.attornatus.models.entities.Address;
import br.com.attornatus.models.enums.AddressRelevanceLevel;
import br.com.attornatus.models.repositories.AddressRepository;
import br.com.attornatus.models.utils.AddressUtil;
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
class AddressServiceSaveImplTest {

    @InjectMocks private AddressServiceSaveImpl underTest;
    @MockBean private AddressRepository repository;

    private Long personId;
    private Address save;
    private Address saved;
    private AddressRelevanceLevel highRelevanceLevel;

    @BeforeEach void setUp() {
        underTest = new AddressServiceSaveImpl(repository);

        personId = 1l;
        highRelevanceLevel = AddressRelevanceLevel.HIGH;
        save = AddressUtil.newAddress();
        saved = AddressUtil.newAddressWithId();
    }

    @Test
    @DisplayName("When Saving Is Successful")
    void whenSaveSuccessful() {
        when(repository.save(save)).thenReturn(saved);

        Address expected = saved;
        Address actual = underTest.apply(save);

        verify(repository, times(1)).save(save);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("When There Is Already An Address As Main For The Person With Id Returns Illegal Argument Exception")
    void whenExistMainAddress() {
        final Address highAddressLevel = AddressUtil.newAddressByRelevanceLevel(highRelevanceLevel);
        final String exceptionMessage = "Uma pessoa não pode ter mais de um endereço principal.";
        when(repository.findHighRelevanceLevelByPersonId(personId))
                .thenReturn(highAddressLevel);

        assertThatThrownBy(() -> underTest.apply(highAddressLevel))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(exceptionMessage);
    }

    @Test
    @DisplayName("When Fails To Save Should Return An Internal Server Error")
    void whenNotSave() {
        final String exceptionMessage = "Não foi possivel completar o cadastro do endereço para a pessoa com o id " +
                "informado devido ao erro interno.";
        when(repository.save(save)).thenReturn(null);

        assertThatThrownBy(() -> underTest.apply(save))
                .isInstanceOf(InternalServerErrorException.class)
                .hasMessageContaining(exceptionMessage);
    }

}