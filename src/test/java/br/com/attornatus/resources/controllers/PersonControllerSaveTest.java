package br.com.attornatus.resources.controllers;

import br.com.attornatus.exceptions.InternalServerErrorException;
import br.com.attornatus.models.entities.Person;
import br.com.attornatus.models.utils.PersonUtil;
import br.com.attornatus.resources.services.PersonServiceSave;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class PersonControllerSaveTest {

    @Autowired private MockMvc mvc;
    @MockBean private PersonServiceSave serviceSave;

    @BeforeEach void setUp() {

    }

    @Test
    @DisplayName("When Save Person Should Return Be OK")
    void whenSaveSuccessful() throws Exception {
        Person save = PersonUtil.newFormEntity();
        Person saved = PersonUtil.newEntity();
        final String contentRequest = "{\"name\": \"Flavia Matos\", \"birth\": \"1990-08-23\"}";
        when(serviceSave.apply(save)).thenReturn(saved);

        mvc.perform(
                post("/v1/api/people")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(contentRequest)
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content.id").value(1l))
                .andExpect(jsonPath("$.content.name").value("Flavia Matos"))
                .andExpect(jsonPath("$.content.birth").value("1990-08-23"));
    }

    @Test
    @DisplayName("When Save Person With Empty Fields Should Return Bad Request")
    void whenSaveEmptyFields() throws Exception {
        Person personEmptyFields = Person.builder().build();
        final String contentRequest = "{}";
        final String exceptionMessage = "Para realizar essa operação as informações da pessoa devem ser passadas.";
        when(serviceSave.apply(personEmptyFields)).thenThrow(new IllegalArgumentException(exceptionMessage));

        mvc.perform(
                post("/v1/api/people")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(contentRequest)
                ).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect((jsonPath("$.message").value(exceptionMessage)));
    }

    @Test
    @DisplayName("When Save Person Fails In Database Should Return An Internal Server Error")
    void whenNotToSave() throws Exception {
        final Person save = PersonUtil.newFormEntity();
        final String contentRequest = "{\"name\": \"Flavia Matos\", \"birth\": \"1990-08-23\"}";
        final String exceptionMessage = "Não foi possivel completar o cadastro da pessoa devido ao erro interno.";
        when(serviceSave.apply(save)).thenThrow(new InternalServerErrorException(exceptionMessage));

        mvc.perform(
                post("/v1/api/people")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(contentRequest)
                ).andDo(print())
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value("INTERNAL_SERVER_ERROR"))
                .andExpect((jsonPath("$.message").value(exceptionMessage)));
    }

}