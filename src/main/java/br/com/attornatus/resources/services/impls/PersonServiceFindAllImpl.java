package br.com.attornatus.resources.services.impls;

import br.com.attornatus.exceptions.NotFoundException;
import br.com.attornatus.models.entities.Person;
import br.com.attornatus.models.repositories.PersonRepository;
import br.com.attornatus.resources.services.PersonServiceFindAll;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class PersonServiceFindAllImpl implements PersonServiceFindAll {

    private final Logger log = LoggerFactory.getLogger(PersonServiceFindAllImpl.class);
    private final PersonRepository repository;

    @Override
    public Page<Person> apply(Map<String, String> pageParams) {
        Pageable pageable = parsePageable(pageParams);
        Page<Person> founded = repository.findAll(pageable);

        throwsExceptionWhenNotFound(founded);
        return founded;
    }

    private Pageable parsePageable(Map<String, String> pageParams) {
        throwsExceptionWhenFieldsAreNotFound(pageParams);
        throwsExceptionWhenFieldsAreBlank(pageParams);
        throwsExceptionWhenPageFieldAreNotNumbers(pageParams);
        throwsExceptionWhenFieldsAreLessThanZero(pageParams);

        Integer page = Integer.parseInt(pageParams.get("page"));
        Integer size = Integer.parseInt(pageParams.get("size"));

        return PageRequest.of(page, size, Sort.by("name").ascending());
    }

    private boolean checkFieldIsNumeric(String field) {
        try {
            Integer.parseInt(field);
            return true;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }

    private void throwsExceptionWhenFieldsAreNotFound(Map<String, String> pageParams) {
        boolean noExistsPageKey = !pageParams.containsKey("page");
        boolean noExistsSizeKey = !pageParams.containsKey("size");

        if (noExistsPageKey || noExistsSizeKey) {
            log.error("Uma tentativa de consulta foi realizada para listar as pessoas cadastradas, mas não foram " +
                    "informados os parêmtros `page` e `size` para realizar a operação.");

            throw new IllegalArgumentException("Não é possivel realizar a operação, os parâmetros informados não são válidos.");
        }
    }

    private void throwsExceptionWhenFieldsAreBlank(Map<String, String> pageParams) {
        boolean isNullPageParam = pageParams.get("page") == "";
        boolean isNullSizeParam = pageParams.get("size") == "";

        if (isNullPageParam || isNullSizeParam) {
            log.error("Uma tentativa de consulta foid realizada para listar as pessoas cadastradas, mas não foi " +
                    "possivel realizar a operação devido ter sido informado os parêmtros `page` e `size` com valores " +
                    "em branco.");

            throw new IllegalArgumentException("Não é possivel realizar a operação, os parâmetros informados não são válidos.");
        }
    }

    private void throwsExceptionWhenPageFieldAreNotNumbers(Map<String, String> pageParams) {
        boolean isPageNotNumeric = !checkFieldIsNumeric(pageParams.get("page"));
        boolean isSizeNotNumeric = !checkFieldIsNumeric(pageParams.get("size"));

        if (isPageNotNumeric || isSizeNotNumeric) {
            log.error("Uma tentativa de consulta foid realizada para listar as pessoas cadastradas, mas os parâmetros " +
                    "informados `page` e `size` não são valores numéricos.");

            throw new IllegalArgumentException("Não é possivel realizar a operação, os parâmetros `page` e `size` não " +
                    "são valores numéricos.");
        }
    }

    private void throwsExceptionWhenFieldsAreLessThanZero(Map<String, String> pageParams) {
        boolean isPageLessThanZero = Integer.parseInt(pageParams.get("page")) < 0;
        boolean isSizeLessThanZero = Integer.parseInt(pageParams.get("size")) < 0;

        if (isPageLessThanZero || isSizeLessThanZero) {
            log.error("Uma tentativa de consulta para organizar a paginação do resulta com valores menores do que zero " +
                    "realizada.");
            throw new IllegalArgumentException("Não é possivel realizar a operação com os parâmetros informados sendo " +
                    "menores do que zero.");
        }
    }

    private void throwsExceptionWhenNotFound(Page<Person> founded) {
        if (founded.isEmpty()) {
            log.warn("Uma tentativa de consulta para listar as pessoas cadastradas no sistema foi realizada, mas não " +
                    "foram encontrados registros.");

            throw new NotFoundException("Não foram encontadas pessoas cadastradas.");
        }
    }


}
