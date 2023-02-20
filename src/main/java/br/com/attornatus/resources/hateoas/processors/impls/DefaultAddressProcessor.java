package br.com.attornatus.resources.hateoas.processors.impls;

import br.com.attornatus.resources.controllers.AddressController;
import br.com.attornatus.resources.hateoas.models.AddressModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class DefaultAddressProcessor {

    public void addLinkSave(AddressModel model) {
        Long personId = model.getContent().getPerson().getId();
        Link link = linkTo(methodOn(AddressController.class)
                .save(null))
                .withRel(IanaLinkRelations.EDIT)
                .withType("application/json;method=POST;charset=UTF-8")
                .withTitle("Save")
                .withName("save");

        model.add(link);
    }

    public void addLinkFindById(AddressModel model) {
        Long addressId = model.getContent().getId();
        Link link = linkTo(methodOn(AddressController.class)
                .findById(addressId))
                .withSelfRel()
                .withType("application/json;method=GET;charset=UTF-8")
                .withTitle("Find By Id (" + addressId + ")")
                .withName("find-by-id");

        model.add(link);
    }

    public void addLinkUpdateById(AddressModel model) {
        Long addressId = model.getContent().getId();
        Link link = linkTo(methodOn(AddressController.class)
                .updateById(addressId, null))
                .withRel(IanaLinkRelations.EDIT)
                .withType("application/json;method=PUT;charset=UTF-8")
                .withTitle("Update by id (" +addressId+ ")")
                .withName("update-by-id");

        model.add(link);
    }

    public void addLinkDeleteByid(AddressModel model) {
        Long addressId = model.getContent().getId();
        Link link = linkTo(methodOn(AddressController.class)
                .deleteById(addressId))
                .withRel(IanaLinkRelations.EDIT)
                .withType("application/json;method=DELETE;charset=UTF-8")
                .withTitle("Delete By Id (" +addressId+ ")")
                .withName("delete-by-id");

        model.add(link);
    }

    public void addLinkFindMainAddressByPersonId(AddressModel model) {
        Long personId = model.getContent().getPerson().getId();
        Link link = linkTo(methodOn(AddressController.class)
                .findMainAddressByPersonId(personId))
                .withRel(IanaLinkRelations.ABOUT)
                .withType("application/json;method=POST;charset=UTF-8")
                .withTitle("Find Main Address")
                .withName("find-main-address");

        model.add(link);
    }

    public void addLinkFindAllByPersonId(AddressModel model) {
        Long personId = model.getContent().getPerson().getId();
        Link link = linkTo(methodOn(AddressController.class)
                .findAllByPersonId(
                        personId,
                        Map.of("page", "0", "size", "10")))
                .withRel(IanaLinkRelations.ABOUT)
                .withType("application/json;method=GET;charset=UTF-8")
                .withTitle("Find All By Person Id")
                .withName("find-all-by-person-id");

        model.add(link);
    }

}
