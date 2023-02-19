package br.com.attornatus.resources.controllers;

import br.com.attornatus.models.entities.Person;
import br.com.attornatus.resources.hateoas.assemblers.PersonHateoasAssembler;
import br.com.attornatus.resources.hateoas.models.PersonModel;
import br.com.attornatus.resources.hateoas.processors.PersonCollectionProcessorHyperMedia;
import br.com.attornatus.resources.hateoas.processors.PersonModelProcessorHyperMedia;
import br.com.attornatus.resources.hateoas.processors.PersonSaveProcessorHyperMedia;
import br.com.attornatus.resources.services.PersonServiceFindAll;
import br.com.attornatus.resources.services.PersonServiceSave;
import br.com.attornatus.utils.LocalDateUtility;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/v1/api/people")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PersonController {

    private Logger log = LoggerFactory.getLogger(PersonController.class);
    private final LocalDateUtility localDateUtil;
    private final PersonHateoasAssembler hateoasAssembler;
    private final PersonSaveProcessorHyperMedia saveProcessorHyperMedia;

    @Qualifier("personServiceSaveImpl") private final PersonServiceSave serviceSave;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PersonModel> save(@RequestBody Person person) {
        Person saved = serviceSave.apply(person);

        PersonModel model = hateoasAssembler.toModel(saved);
        model = saveProcessorHyperMedia.process(model);
        return ResponseEntity.ok(model);
    }

}
