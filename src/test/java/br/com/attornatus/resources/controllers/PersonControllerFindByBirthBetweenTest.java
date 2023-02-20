package br.com.attornatus.resources.controllers;

import br.com.attornatus.exceptions.NotFoundException;
import br.com.attornatus.models.utils.PersonUtil;
import br.com.attornatus.resources.services.impls.PersonServiceFindByBirthBetweenImpl;
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

import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class PersonControllerFindByBirthBetweenTest {

    @Autowired private MockMvc mvc;
    @MockBean private PersonServiceFindByBirthBetweenImpl serviceFindByBirthBetween;

    @Test
    @DisplayName("When Find By Birth Between Persons Should Ok")
    void whenSuccessfulReturnOk() throws Exception {
        final String startBirthString = "01-01-1990";
        final String endBirthString = "01-12-2000";
        final Map<String, String> pageParams = Map.of("page", "0", "size", "10");
        when(serviceFindByBirthBetween.apply(startBirthString, endBirthString, pageParams))
                .thenReturn(PersonUtil.newSingleEntityInPage());

        mvc.perform(get("/v1/api/people/birth/between/01-01-1990/01-12-2000?page=0&size=10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.parseMediaType("application/hap+json")))
                .andExpect(jsonPath("$.content[0].content.id").value(1L))
                .andExpect(jsonPath("$.content[0].content.name").value("Flavia Matos"));
    }

    @Test
    @DisplayName("When Find By Birth Between Should Not Found")
    void whenNotFound() throws Exception {
        final String startBirthString = "01-01-1990";
        final String endBirthString = "01-12-2000";
        final Map<String, String> pageParams = Map.of("page", "0", "size", "10");
        final String exceptionMsg = "Não foram encontradas pessoas entre as datas de nascimento informadas.";
        when(serviceFindByBirthBetween.apply(startBirthString, endBirthString, pageParams))
                .thenThrow(new NotFoundException(exceptionMsg));

        mvc.perform(get("/v1/api/people/birth/between/01-01-1990/01-12-2000?page=0&size=10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value("NOT_FOUND"))
                .andExpect((jsonPath("$.message").value(exceptionMsg)));
    }

}
