package br.com.attornatus.resources.services.impls;

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

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class AddressServiceFindMainByPersonIdImplTest {

    @InjectMocks private AddressServiceFindMainByPersonIdImpl underTest;
    @MockBean private IdServiceUtil idServiceUtil;
    @MockBean private AddressRepository repository;

    private Long personId;
    private Address founded;

    @BeforeEach void setUp() {
        underTest = new AddressServiceFindMainByPersonIdImpl(idServiceUtil, repository);

        personId = 1l;
        AddressRelevanceLevel high = AddressRelevanceLevel.HIGH;
        founded = AddressUtil.newAddressByRelevanceLevelWithId(high);
    }

    @Test
    @DisplayName("When Find Main Address By People Id And Returns Successful")
    void whenFindMainAddressReturnsSuccessful() {
        when(repository.findHighRelevanceLevelByPersonId(personId))
                .thenReturn(founded);

        Address expected = founded;
        Address actual = underTest.apply(personId);
        verify(repository, times(1))
                .findHighRelevanceLevelByPersonId(personId);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("When Not Found Main Address By Person Id It Return Not Found Exception")
    void whenNotFound() {
        final String exceptionMessage = "Não foi possivel encontrar o endereço principal da pessoa indicada.";
        when(repository.findHighRelevanceLevelByPersonId(personId))
                .thenReturn(null);

        assertThatThrownBy(() -> underTest.apply(personId))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining(exceptionMessage);
    }
    
}