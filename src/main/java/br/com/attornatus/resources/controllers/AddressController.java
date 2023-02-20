package br.com.attornatus.resources.controllers;

import br.com.attornatus.models.entities.Address;
import br.com.attornatus.resources.hateoas.models.AddressModel;
import br.com.attornatus.resources.hateoas.processors.AddressProcessor;
import br.com.attornatus.resources.services.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/v1/api/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService service;
    private final AddressProcessor processorHyperMedia;

    @PostMapping
    public ResponseEntity<AddressModel> save(@RequestBody Address address) {
        Address saved = service.save(address);
        AddressModel model = processorHyperMedia.save(saved);
        return ResponseEntity.ok(model);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressModel> findById(@PathVariable Long id) {
        Address founded = service.findById(id);
        AddressModel model = processorHyperMedia.findById(founded);
        return ResponseEntity.ok(model);
    }

    @GetMapping("/main/person/{personId}")
    public ResponseEntity<AddressModel> findMainAddressByPersonId(@PathVariable Long personId) {
        Address founded = service.findMainAddressByPersonId(personId);
        AddressModel model = processorHyperMedia.findMainAddressByPersonId(founded);
        return ResponseEntity.ok(model);
    }

    @GetMapping("/person/{personId}")
    public ResponseEntity<CollectionModel<AddressModel>> findAllByPersonId(@PathVariable Long personId,
                                                                           @RequestParam Map<String, String> pageParams) {
        Page<Address> founded = service.findAllByPersonId(personId, pageParams);
        CollectionModel<AddressModel> models = processorHyperMedia.findAllByPersonId(founded);
        return ResponseEntity.ok(models);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddressModel> updateById(@PathVariable Long id, @RequestBody Address address) {
        Address updated = service.updateById(id, address);
        AddressModel model = processorHyperMedia.udpateById(updated);
        return ResponseEntity.ok(model);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
