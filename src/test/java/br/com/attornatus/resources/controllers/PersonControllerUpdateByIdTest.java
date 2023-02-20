package br.com.attornatus.resources.controllers;

import br.com.attornatus.exceptions.InternalServerErrorException;
import br.com.attornatus.exceptions.NotFoundException;
import br.com.attornatus.models.entities.Person;
import br.com.attornatus.models.utils.PersonUtil;
import br.com.attornatus.resources.services.impls.PersonServiceUpdateByIdImpl;
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

import java.time.LocalDate;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class PersonControllerUpdateByIdTest {

    @Autowired private MockMvc mvc;
    @MockBean private PersonServiceUpdateByIdImpl serviceUpdateById;

    private Long id;
    private Person update;
    private Person updated;
    private String requestBody;

    @BeforeEach
    void setUp() {
        id = 1l;
        LocalDate newBirth = LocalDate.of(1992, 05, 21);
        update = PersonUtil.newEntityByBirth(newBirth);
        updated = PersonUtil.newEntityByBirthWithId(newBirth);

        requestBody = "{ \"name\": \"Flavia Matos\", \"birth\": \"1992-05-21\" }";
    }

    @Test
    @DisplayName("When Updated By Id Should Returns Ok")
    void whenUpdateByIdSuccessful() throws Exception {
        when(serviceUpdateById.apply(id, update)).thenReturn(updated);

        mvc.perform(put("/v1/api/people/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content.id").value(1l))
                .andExpect(jsonPath("$.content.name").value("Flavia Matos"))
                .andExpect(jsonPath("$.content.birth").value("1992-05-21"));
    }

    @Test
    @DisplayName("When Update By Id Should Return Not Found")
    void whenNotFoundById() throws Exception {
        final String exceptionMessage = "Não foi possivel encontrar a pessoa indicada pelo id informado.";
        when(serviceUpdateById.apply(id, update)).thenThrow(new NotFoundException(exceptionMessage));

        mvc.perform(put("/v1/api/people/"+id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value("NOT_FOUND"))
                .andExpect(jsonPath("$.message").value(exceptionMessage));
    }

    @Test
    @DisplayName("When Update By Id But Unable To Return Updated Person Resulting Internal Server Error")
    void whenNotUpdated() throws Exception {
        final String exceptionMessage = "Não foi possivel encontrar a pessoa atualizada.";
        when(serviceUpdateById.apply(id, update)).thenThrow(new InternalServerErrorException(exceptionMessage));

        mvc.perform(put("/v1/api/people/"+id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andDo(print())
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value("INTERNAL_SERVER_ERROR"))
                .andExpect(jsonPath("$.message").value(exceptionMessage));
    }

}
