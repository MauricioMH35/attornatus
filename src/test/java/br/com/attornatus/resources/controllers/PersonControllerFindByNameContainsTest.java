package br.com.attornatus.resources.controllers;

import br.com.attornatus.exceptions.NotFoundException;
import br.com.attornatus.models.entities.Person;
import br.com.attornatus.models.utils.PersonUtil;
import br.com.attornatus.resources.services.impls.PersonServiceFindByNameContainsImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
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
public class PersonControllerFindByNameContainsTest {

    @Autowired private MockMvc mvc;
    @MockBean private PersonServiceFindByNameContainsImpl serviceFindByNameContains;

    @Test
    @DisplayName("When Find By Name Contains Persons Should Ok")
    void whenSuccessReturnOk() throws Exception {
        final String name = "Flavia";
        final Map<String, String> pageParams = Map.of("page", "0", "size", "10");
        final Page<Person> founded = PersonUtil.newSingleEntityInPage();
        when(serviceFindByNameContains.apply(name, pageParams)).thenReturn(founded);

        mvc.perform(get("/v1/api/people/name/"+name+"?page=0&size=10")
                        .contentType(MediaType.parseMediaType("application/hal+json"))
                        .accept(MediaType.parseMediaType("application/hal+json"))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.parseMediaType("application/hal+json")))
                .andExpect(jsonPath("$._embedded.personModelList[0].content.id").value(1l))
                .andExpect(jsonPath("$._embedded.personModelList[0].content.name").value("Flavia Matos"))
                .andExpect(jsonPath("$._embedded.personModelList[0].content.birth").value("1990-08-23"));
    }

    @Test
    @DisplayName("When Find By Name Contains Persons With Not Found Should Not Found")
    void whenNotFoundPersons() throws Exception {
        final String name = "Flavia";
        final Map<String, String> pageParams = Map.of("page", "0", "size", "10");
        final String exceptionMessage = "N??o foram encontadas pessoas cadastradas.";
        when(serviceFindByNameContains.apply(name, pageParams)).thenThrow(new NotFoundException(exceptionMessage));

        mvc.perform(get("/v1/api/people/name/"+name+"?page=0&size=10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value("NOT_FOUND"))
                .andExpect((jsonPath("$.message").value(exceptionMessage)));
    }

}
