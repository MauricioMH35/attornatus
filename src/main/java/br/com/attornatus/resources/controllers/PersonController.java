package br.com.attornatus.resources.controllers;

import br.com.attornatus.models.entities.Person;
import br.com.attornatus.resources.hateoas.assemblers.PersonHateoasAssembler;
import br.com.attornatus.resources.hateoas.models.PersonModel;
import br.com.attornatus.resources.hateoas.processors.PersonCollectionProcessorHyperMedia;
import br.com.attornatus.resources.hateoas.processors.PersonModelProcessorHyperMedia;
import br.com.attornatus.resources.hateoas.processors.PersonSaveProcessorHyperMedia;
import br.com.attornatus.resources.services.PersonServiceFindAll;
import br.com.attornatus.resources.services.PersonServiceFindByBirth;
import br.com.attornatus.resources.services.PersonServiceFindById;
import br.com.attornatus.resources.services.PersonServiceSave;
import br.com.attornatus.resources.services.impls.PersonServiceFindByNameContainsImpl;
import br.com.attornatus.utils.LocalDateUtility;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final PersonModelProcessorHyperMedia modelProcessorHyperMedia;
    private final PersonCollectionProcessorHyperMedia collectionProcessorHyperMedia;

    private final PersonServiceSave serviceSave;
    private final PersonServiceFindAll serviceFindAll;
    private final PersonServiceFindById serviceFindById;
    private final PersonServiceFindByNameContainsImpl serviceFindByNameContains;
    private final PersonServiceFindByBirth serviceFindByBirth;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PersonModel> save(@RequestBody Person person) {
        Person saved = serviceSave.apply(person);

        PersonModel model = hateoasAssembler.toModel(saved);
        model = saveProcessorHyperMedia.process(model);
        return ResponseEntity.ok(model);
    }

    @GetMapping(produces = "application/hal+json")
    public ResponseEntity<CollectionModel<PersonModel>> findAll(@RequestParam Map<String, String> pageParams) {
        Page<Person> founded = serviceFindAll.apply(pageParams);

        CollectionModel<PersonModel> models = hateoasAssembler.toCollectionModel(founded.toList());
        collectionProcessorHyperMedia.process(models);
        return ResponseEntity.ok(models);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PersonModel> findById(@PathVariable Long id) {
        Person founded = serviceFindById.apply(id);

        PersonModel model = hateoasAssembler.toModel(founded);
        model = modelProcessorHyperMedia.process(model);
        return ResponseEntity.ok(model);
    }

    @GetMapping(path = "/name/{name}", produces = "application/hal+json")
    public ResponseEntity<CollectionModel<PersonModel>> findByNameContains(@PathVariable String name,
                                                                           @RequestParam Map<String, String> pageParams) {
        Page<Person> founded = serviceFindByNameContains.apply(name, pageParams);

        CollectionModel<PersonModel> models = hateoasAssembler.toCollectionModel(founded.toList());
        collectionProcessorHyperMedia.process(models);
        return ResponseEntity.ok(models);
    }

    @GetMapping(path = "/birth/{birth}", produces = "application/hal+json")
    public ResponseEntity<CollectionModel<PersonModel>> findByBirth(@PathVariable String birth,
                                                                    @RequestParam Map<String, String> pageParams) {
        Page<Person> founded = serviceFindByBirth.apply(birth, pageParams);

        CollectionModel<PersonModel> models = hateoasAssembler.toCollectionModel(founded.toList());
        collectionProcessorHyperMedia.process(models);
        return ResponseEntity.ok(models);
    }

    @GetMapping(path = "/birth/between", produces = "application/hap+json")
    public ResponseEntity<PersonModel> findByBirthBetween(@RequestParam Map<String, String> betweenParams,
                                                          @RequestParam Map<String, String> pageParams) {
        Integer page = Integer.parseInt(pageParams.get("page"));
        Integer pageSize = Integer.parseInt(pageParams.get("size"));
        LocalDate startBirth = localDateUtil.parseLocalDate(betweenParams.get("start"));
        LocalDate endBirth = localDateUtil.parseLocalDate(betweenParams.get("end"));
        return null;
    }

    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PersonModel> updateById(@PathVariable Long id, @RequestBody Person person) {
        return null;
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<PersonModel> deleteById(@PathVariable Long id) {
        return null;
    }

}
