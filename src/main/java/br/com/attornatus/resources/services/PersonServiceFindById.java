package br.com.attornatus.resources.services;

import br.com.attornatus.models.entities.Person;
import org.springframework.stereotype.Service;

@Service
public interface PersonServiceFindById {

    Person apply(Long id);

}
