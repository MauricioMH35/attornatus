package br.com.attornatus.models.utils;

import br.com.attornatus.models.entities.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.time.LocalDate;
import java.util.List;

public class PersonUtil {

    public static Person newFormEntity() {
        return Person.builder()
                .name("Flavia Matos")
                .birth(LocalDate.of(1990, 8, 23))
                .build();
    }

    public static Person newEntity() {
        return Person.builder()
                .id(1l)
                .name("Flavia Matos")
                .birth(LocalDate.of(1990, 8, 23))
                .build();
    }

    public static List<Person> newPersonsInList() {
        return List.of(
                Person.builder()
                        .id(1l)
                        .name("Flavia Matos")
                        .birth(LocalDate.of(1990, 8, 23))
                        .build(),
                Person.builder()
                        .id(2l)
                        .name("Malu da Raimundo")
                        .birth(LocalDate.of(1992, 10, 03))
                        .build(),
                Person.builder()
                        .id(3l)
                        .name("Rebeca Isadora Martins")
                        .birth(LocalDate.of(1995, 05, 23))
                        .build()
        );
    }

    public static Page<Person> newPersonsInPage() {
        return new PageImpl<Person>(newPersonsInList());
    }

}
