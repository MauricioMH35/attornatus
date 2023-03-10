package br.com.attornatus.resources.controllers;

import br.com.attornatus.exceptions.NotFoundException;
import br.com.attornatus.models.entities.Person;
import br.com.attornatus.models.utils.PersonUtil;
import br.com.attornatus.resources.services.impls.PersonServiceFindByIdImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class PersonControllerFindByIdTest {

    @Autowired private MockMvc mvc;
    @MockBean private PersonServiceFindByIdImpl serviceFindById;

    @BeforeEach void setUp() {

    }

    @Test
    @DisplayName("When Find By Id Person Should Return Person Founded")
    void whenFindByIdReturnPersonFounded() throws Exception {
        final Long id = 1l;
        final Person founded = PersonUtil.newEntity();
        when(serviceFindById.apply(id)).thenReturn(founded);

        mvc.perform(get("/v1/api/people/"+id)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect((jsonPath("$.content.id").value(1l)))
                .andExpect((jsonPath("$.content.name").value("Flavia Matos")))
                .andExpect((jsonPath("$.content.birth").value("1990-08-23")));
    }

    @Test
    @DisplayName("When Find By Id Person With Id Less Than One Should Bad Request")
    void whenFindByIdLessThanOne() throws Exception {
        final Long id = -1l;
        final String exceptionMessage = "Para realizar essa opera????o o id da pessoa deve ser maior do que um.";
        when(serviceFindById.apply(id)).thenThrow(new IllegalArgumentException(exceptionMessage));

        mvc.perform(get("/v1/api/people/"+id)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect((jsonPath("$.message").value(exceptionMessage)));
    }

    @Test
    @DisplayName("When Find By Id Person But Not Found In Database Must Not Found")
    void whenFindByIdNotFound() throws Exception {
        final Long id = 1l;
        final String exceptionMessage = "N??o foi possivel encontrar a pessoa com o id (" + id + ") informado.";
        when(serviceFindById.apply(id)).thenThrow(new NotFoundException(exceptionMessage));

        mvc.perform(get("/v1/api/people/"+id)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.status").value("NOT_FOUND"))
                .andExpect((jsonPath("$.message").value(exceptionMessage)));
    }

}
