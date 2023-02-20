package br.com.attornatus.resources.services;

import br.com.attornatus.models.entities.Person;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface PersonServiceFindByBirthBetween {

    Page<Person> apply(String start, String end, Map<String, String> pageParams);

}
