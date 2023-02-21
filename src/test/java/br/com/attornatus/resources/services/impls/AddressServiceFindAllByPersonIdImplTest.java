package br.com.attornatus.resources.services.impls;

import br.com.attornatus.exceptions.NotFoundException;
import br.com.attornatus.models.entities.Address;
import br.com.attornatus.models.repositories.AddressRepository;
import br.com.attornatus.models.utils.AddressUtil;
import br.com.attornatus.resources.services.utils.IdServiceUtil;
import br.com.attornatus.resources.services.utils.PageableServiceUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class AddressServiceFindAllByPersonIdImplTest {

    @InjectMocks private AddressServiceFindAllByPersonIdImpl underTest;
    @MockBean private IdServiceUtil idServiceUtil;
    @MockBean private PageableServiceUtil pageableServiceUtil;
    @MockBean private AddressRepository repository;

    private Long personId;
    private Page<Address> founded;
    private Pageable pageable;
    private Map<String, String> pageParams;

    @BeforeEach void setUp() {
        pageableServiceUtil = new PageableServiceUtil();
        underTest = new AddressServiceFindAllByPersonIdImpl(idServiceUtil, pageableServiceUtil, repository);

        personId = 1l;
        founded = AddressUtil.newAddressByPersonInPage();
        pageable = PageRequest.of(0, 10, Sort.by("publicPlace").ascending());
        pageParams = Map.of("page", "0", "size", "10");
    }

    @Test
    @DisplayName("When Find All By Person Id It Returns Successful")
    void whenFindAllByPersonIdReturnsSuccessful() {
        when(repository.findByPersonId(personId, pageable)).thenReturn(founded);

        Page<Address> expected = founded;
        Page<Address> actual = underTest.apply(personId, pageParams);

        verify(repository, times(1)).findByPersonId(personId, pageable);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("When Find All By Person Id It Returns Not Found Exception")
    void whenNotFound() {
        final String exceptionMessage = "Não foi possivel encontrar os endereços referente a pessoa indicado pelo id.";
        when(repository.findByPersonId(personId, pageable)).thenReturn(Page.empty());

        assertThatThrownBy(() -> underTest.apply(personId, pageParams))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining(exceptionMessage);
    }

}
