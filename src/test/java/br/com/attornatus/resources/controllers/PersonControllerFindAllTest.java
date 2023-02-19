package br.com.attornatus.resources.controllers;

import br.com.attornatus.exceptions.NotFoundException;
import br.com.attornatus.models.entities.Person;
import br.com.attornatus.models.utils.PersonUtil;
import br.com.attornatus.resources.services.PersonServiceFindAll;
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
public class PersonControllerFindAllTest {

    @Autowired private MockMvc mvc;
    @MockBean private PersonServiceFindAll serviceFindAll;

    @Test
    @DisplayName("When Find All Persons Should Return Collection Model of Person Models Status Ok")
    void whenSuccessReturnCollectionModel() throws Exception {
        final Map<String, String> pageParams = Map.of("page", "0", "size", "10");
        final Page<Person> founded = PersonUtil.newPersonsInPage();
        when(serviceFindAll.apply(pageParams)).thenReturn(founded);

        mvc.perform(get("/v1/api/people?page=0&size=10")
                        .contentType(MediaType.parseMediaType("application/hal+json"))
                        .accept(MediaType.parseMediaType("application/hal+json"))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.parseMediaType("application/hal+json")))
                .andExpect(jsonPath("$._embedded.personModelList[0].content.id").value(1l))
                .andExpect(jsonPath("$._embedded.personModelList[0].content.name").value("Flavia Matos"))
                .andExpect(jsonPath("$._embedded.personModelList[0].content.birth").value("1990-08-23"))
                .andExpect(jsonPath("$._embedded.personModelList[1].content.id").value(2l))
                .andExpect(jsonPath("$._embedded.personModelList[1].content.name").value("Malu da Raimundo"))
                .andExpect(jsonPath("$._embedded.personModelList[1].content.birth").value("1992-10-03"))
                .andExpect(jsonPath("$._embedded.personModelList[2].content.id").value(3l))
                .andExpect(jsonPath("$._embedded.personModelList[2].content.name").value("Rebeca Isadora Martins"))
                .andExpect(jsonPath("$._embedded.personModelList[2].content.birth").value("1995-05-23"));
    }

    @Test
    @DisplayName("When Find All Persons With Not Found Should Not Found")
    void whenNotFoundPersons() throws Exception {
        final Map<String, String> pageParams = Map.of("page", "0", "size", "10");
        final String exceptionMessage = "Não foram encontadas pessoas cadastradas.";
        when(serviceFindAll.apply(pageParams)).thenThrow(new NotFoundException(exceptionMessage));

        mvc.perform(get("/v1/api/people?page=0&size=10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value("NOT_FOUND"))
                .andExpect((jsonPath("$.message").value(exceptionMessage)));
    }

}
