package br.com.attornatus.resources.services;

import br.com.attornatus.models.entities.Person;
import org.springframework.stereotype.Service;

@Service
public interface PersonServiceUpdateById {

    Person apply(Long id, Person person);

}
