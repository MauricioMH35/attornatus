package br.com.attornatus.resources.controllers;

import br.com.attornatus.exceptions.NotFoundException;
import br.com.attornatus.models.entities.Person;
import br.com.attornatus.resources.services.impls.PersonServiceDeleteByIdImpl;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class PersonControllerDeleteByIdTest {

    @Autowired private MockMvc mvc;
    @MockBean private PersonServiceDeleteByIdImpl serviceDeleteById;

    private Long id;

    @BeforeEach void setUp() {
        id = 1l;
    }

    @Test
    @DisplayName("When Updated By Id Retuns Ok")
    void whenUpdateSuccessful() throws Exception {
        when(serviceDeleteById.apply(id)).thenReturn(true);

        mvc.perform(delete("/v1/api/people/"+id)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("When Not Found By Id Return Not Found")
    void whenNotFoundedById() throws Exception {
        final String exceptionMessage = "Não foi possivel encontrar a pessoa indicada pelo id informado.";
        when(serviceDeleteById.apply(id)).thenThrow(new NotFoundException(exceptionMessage));

        mvc.perform(delete("/v1/api/people/"+id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value("NOT_FOUND"))
                .andExpect((jsonPath("$.message").value(exceptionMessage)));
    }

}
