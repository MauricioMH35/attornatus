package br.com.attornatus.resources.services.impls;

import br.com.attornatus.models.repositories.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class PersonServiceFindByNameContainsImplTest {

    @InjectMocks private PersonServiceFindByNameContainsImpl underTest;
    @MockBean private PersonRepository repository;

    @BeforeEach void setUp() {
        underTest = new PersonServiceFindByNameContainsImpl(repository);
    }

}
