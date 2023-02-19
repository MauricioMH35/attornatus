package br.com.attornatus.resources.services.impls;

import br.com.attornatus.models.entities.Person;
import br.com.attornatus.models.repositories.PersonRepository;
import br.com.attornatus.resources.services.PersonServiceFindById;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PersonServiceFindByIdImpl implements PersonServiceFindById {

    private final Logger log = LoggerFactory.getLogger(PersonServiceFindByIdImpl.class);
    private final PersonRepository repository;

    @Override
    public Person apply(Long id) {
        throwsExceptionWhenEmptyId(id);
        throwsExceptionWhenIdLessThanOne(id);

        Person founded = repository.findById(id).get();

        return founded;
    }

    private void throwsExceptionWhenEmptyId(Long id) {
        if (id == null) {
            log.error("Houve uma tentativa de consultar uma pessoa utilizando do seu id, no entanto o parâmetro " +
                    "informado pelo cliente é nulo.");
            throw new IllegalArgumentException("Para realizar essa operação o id da pessoa deve ser informado.");
        }
    }

    private void throwsExceptionWhenIdLessThanOne(Long id) {
        if (id < 1) {
            log.error("Houve uma tentatia de consultar uma pessoa utilizando um id (" + id + ") menor do que 1.");
            throw new IllegalArgumentException("Para realizar essa operação o id da pessoa deve ser maior do que um.");
        }
    }

}
