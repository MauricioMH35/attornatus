package br.com.attornatus.resources.hateoas.processors.impls;

import br.com.attornatus.resources.controllers.AddressController;
import br.com.attornatus.resources.controllers.PersonController;
import br.com.attornatus.resources.hateoas.models.PersonModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class DefaultPersonProcessor {

    public void addLinkSave(PersonModel model) {
        Link link = linkTo(methodOn(PersonController.class)
                .save(null))
                .withRel(IanaLinkRelations.EDIT)
                .withType("application/json;method=POST;charset=UTF-8")
                .withTitle("Save")
                .withName("save")
                .withDeprecation("Register a new person");

        model.add(link);
    }

    public void addLinkSave(CollectionModel<PersonModel> models) {
        Link link = linkTo(methodOn(PersonController.class)
                .save(null))
                .withRel(IanaLinkRelations.EDIT)
                .withType("application/json;method=POST;charset=UTF-8")
                .withTitle("Save")
                .withName("save")
                .withDeprecation("Register a new person");

        models.add(link);
    }

    public void addLinkFindById(PersonModel model) {
        Long personId = model.getContent().getId();
        Link link = linkTo(
                methodOn(PersonController.class)
                        .findById(model.getContent().getId()))
                .withSelfRel()
                .withType("application/json;method=GET;charset=UTF-8")
                .withTitle("Find By Id (" + personId + ")")
                .withName("find-by-id")
                .withDeprecation("Find person by id (" + personId + ") indicated by request param");

        model.add(link);
    }

    public void addLinkUpdateById(PersonModel model) {
        Long personId = model.getContent().getId();
        Link link = linkTo(
                methodOn(PersonController.class)
                        .updateById(model.getContent().getId(), null))
                .withRel(IanaLinkRelations.EDIT)
                .withType("application/json;method=PUT;charset=UTF-8")
                .withTitle("Update by id (" +personId+ ")")
                .withName("update-by-id")
                .withDeprecation("Update person by id (" + personId + ") indicated by request param");

        model.add(link);
    }

    public void addLinkDeleteById(PersonModel model) {
        Long personId = model.getContent().getId();
        Link link = linkTo(methodOn(PersonController.class).deleteById(personId))
                .withRel(IanaLinkRelations.EDIT)
                .withType("application/json;method=DELETE;charset=UTF-8")
                .withTitle("Delete By Id (" + personId + ")")
                .withName("delete-by-id")
                .withDeprecation("Delete person by id (" + personId + ") indicated by request param");

        model.add(link);
    }

    public void addLinkAddAddressByPersonId(PersonModel model) {
        Long personId = model.getContent().getId();
        Link link = linkTo(methodOn(AddressController.class)
                .save(null))
                .withRel(IanaLinkRelations.EDIT)
                .withType("application/json;method=POST;charset=UTF-8")
                .withTitle("Add Address")
                .withName("add-address")
                .withDeprecation("Add an address for this person id (" + personId + ") indicated by request param");

        model.add(link);
    }

    public void addLinkFindMainAddressByPersonId(PersonModel model) {
        Long personId = model.getContent().getId();
        Link link = linkTo(methodOn(AddressController.class)
                .findMainAddressByPersonId(personId))
                .withRel(IanaLinkRelations.ABOUT)
                .withType("application/json;method=POST;charset=UTF-8")
                .withTitle("Find Main Address")
                .withName("find-main-address");

        model.add(link);
    }

    public void addLinkFindAllAddressesByPersonId(PersonModel model) {
        Long personId = model.getContent().getId();
        Link link = linkTo(methodOn(AddressController.class)
                .findAllByPersonId(personId, Map.of("page", "0", "size", "10")))
                .withRel(IanaLinkRelations.ABOUT)
                .withType("application/json;method=POST;charset=UTF-8")
                .withTitle("Find All Addresses")
                .withName("find-all-addresses");

        model.add(link);
    }

    public void addLinkFindAll(PersonModel model) {
        Link link = linkTo(methodOn(PersonController.class)
                .findAll(Map.of(
                        "page", "0",
                        "size", "10"
                )))
                .withRel(IanaLinkRelations.COLLECTION)
                .withType("application/json;method=GET;charset=UTF-8")
                .withTitle("Find All")
                .withName("find-all");

        model.add(link);
    }

    public void addLinkFindByBirth(PersonModel model) {
        Link link = linkTo(methodOn(PersonController.class)
                .findByBirth(
                        model.getContent().getBirth().toString(),
                        Map.of("page", "0", "size", "10")))
                .withRel(IanaLinkRelations.EDIT)
                .withType("application/json;method=POST;charset=UTF-8")
                .withTitle("Find By Birth")
                .withName("find-by-birth");

        model.add(link);
    }

    public void addLinkFindByBirth(CollectionModel<PersonModel> models) {
        Link link = linkTo(methodOn(PersonController.class)
                .findByBirth(
                        "1990-01-01",
                        Map.of("page", "0", "size", "10")))
                .withRel(IanaLinkRelations.EDIT)
                .withType("application/json;method=POST;charset=UTF-8")
                .withTitle("Find By Birth")
                .withName("find-by-birth");

        models.add(link);
    }

    public void addLinkFindByBirthBetween(PersonModel model) {
        Link link = linkTo(methodOn(PersonController.class)
                .findByBirthBetween(
                        model.getContent().getBirth().toString(),
                        "2023-01-01",
                        Map.of("page", "0", "size", "10")))
                .withRel(IanaLinkRelations.EDIT)
                .withType("application/json;method=POST;charset=UTF-8")
                .withTitle("Find By Birth")
                .withName("find-by-birth");

        model.add(link);
    }

    public void addLinkFindByBirthBetween(CollectionModel<PersonModel> models) {
        Link link = linkTo(methodOn(PersonController.class)
                .findByBirthBetween(
                        "1990-01-01","2023-01-01",
                        Map.of("page", "0", "size", "10")))
                .withRel(IanaLinkRelations.EDIT)
                .withType("application/json;method=POST;charset=UTF-8")
                .withTitle("Find By Birth")
                .withName("find-by-birth");

        models.add(link);
    }

}
