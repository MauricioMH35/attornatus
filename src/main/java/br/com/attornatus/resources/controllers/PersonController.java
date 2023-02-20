package br.com.attornatus.resources.controllers;

import br.com.attornatus.models.entities.Person;
import br.com.attornatus.resources.hateoas.models.PersonModel;
import br.com.attornatus.resources.hateoas.processors.PersonProcessor;
import br.com.attornatus.resources.services.PersonService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/v1/api/people")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PersonController {

    private Logger log = LoggerFactory.getLogger(PersonController.class);
    private final PersonProcessor hyperMedia;
    private final PersonService service;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PersonModel> save(@RequestBody Person person) {
        Person saved = service.save(person);
        PersonModel model = hyperMedia.save(saved);
        return ResponseEntity.ok(model);
    }

    @GetMapping(produces = "application/hal+json")
    public ResponseEntity<CollectionModel<PersonModel>> findAll(@RequestParam Map<String, String> pageParams) {
        Page<Person> founded = service.findAll(pageParams);
        CollectionModel<PersonModel> models = hyperMedia.findAll(founded);
        return ResponseEntity.ok(models);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PersonModel> findById(@PathVariable Long id) {
        Person founded = service.findById(id);
        PersonModel model = hyperMedia.findById(founded);
        return ResponseEntity.ok(model);
    }

    @GetMapping(path = "/name/{name}", produces = "application/hal+json")
    public ResponseEntity<CollectionModel<PersonModel>> findByNameContains(
            @PathVariable String name,
            @RequestParam Map<String, String> pageParams
    ) {
        Page<Person> founded = service.findByNameContains(name, pageParams);
        CollectionModel<PersonModel> models = hyperMedia.findByNameContains(founded);
        return ResponseEntity.ok(models);
    }

    @GetMapping(path = "/birth/{birth}", produces = "application/hal+json")
    public ResponseEntity<CollectionModel<PersonModel>> findByBirth(
            @PathVariable String birth,
            @RequestParam Map<String, String> pageParams
    ) {
        Page<Person> founded = service.findByBirth(birth, pageParams);
        CollectionModel<PersonModel> models = hyperMedia.findByBirth(founded);
        return ResponseEntity.ok(models);
    }

    @GetMapping(path = "/birth/between/{start}/{end}", produces = "application/hap+json")
    public ResponseEntity<CollectionModel<PersonModel>> findByBirthBetween(
            @PathVariable String start,
            @PathVariable String end,
            @RequestParam Map<String, String> pageParams
    ) {
        Page<Person> founded = service.findByBirthBetween(start, end, pageParams);
        CollectionModel<PersonModel> models = hyperMedia.findByBirthBetween(founded);
        return ResponseEntity.ok(models);
    }

    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PersonModel> updateById(
            @PathVariable Long id,
            @RequestBody Person person
    ) {
        Person updated = service.updateById(id, person);
        PersonModel model = hyperMedia.updateById(updated);
        return ResponseEntity.ok(model);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<PersonModel> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity
                .noContent()
                .build();
    }

}
