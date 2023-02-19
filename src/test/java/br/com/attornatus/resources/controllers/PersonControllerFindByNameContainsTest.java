package br.com.attornatus.resources.controllers;

import br.com.attornatus.resources.services.impls.PersonServiceFindByNameContainsImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class PersonControllerFindByNameContainsTest {

    @Autowired private MockMvc mvc;
    @MockBean private PersonServiceFindByNameContainsImpl serviceFindByNameContains;

    @BeforeEach void setUp() {

    }

}
