package br.com.attornatus.resources.services.impls;

import br.com.attornatus.exceptions.InternalServerErrorException;
import br.com.attornatus.models.entities.Person;
import br.com.attornatus.models.repositories.PersonRepository;
import br.com.attornatus.resources.services.PersonServiceSave;
import br.com.attornatus.utils.LocalDateUtility;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PersonServiceSaveImpl implements PersonServiceSave {

    private final Logger log = LoggerFactory.getLogger(PersonServiceSaveImpl.class);
    private final LocalDateUtility localDateUtil;
    private final PersonRepository repository;

    private String personName;

    @Override
    public Person apply(Person person) {
        throwsExceptionWhenIsFieldsAreBlank(person);
        return onSave(person);
    }

    private void setPersonName(String personName) {
        this.personName = personName;
    }

    private Person onSave(Person person) {
        Person saved = repository.save(person);
        setPersonName(person.getName());
        throwsExceptionWhenNotToSave(saved);
        return saved;
    }

    private boolean isFieldsAreBlank(Person person) {
        return person.equals(Person.builder().build());
    }

    private void throwsExceptionWhenIsFieldsAreBlank(Person person) {
        if (isFieldsAreBlank(person)) {
            log.error("Uma tentativa de realizar um registro falho, quando informado uma pessoa nula");
            throw new IllegalArgumentException("Para realizar essa operação as informações da pessoa devem ser passadas.");
        }
    }

    private void throwsExceptionWhenNotToSave(Person person) {
        if (person == null) {
            log.error("Houve um tentativa de registro da pessoa (" + personName + "), mas não foi cadastrada com sucesso na base de dados.");
            throw new InternalServerErrorException("Não foi possivel completar o cadastro da pessoa devido ao erro interno.");
        }
    }

}
