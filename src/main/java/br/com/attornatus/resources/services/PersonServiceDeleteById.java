package br.com.attornatus.resources.services;

import org.springframework.stereotype.Service;

@Service
public interface PersonServiceDeleteById {

    Boolean apply(Long id);

}
