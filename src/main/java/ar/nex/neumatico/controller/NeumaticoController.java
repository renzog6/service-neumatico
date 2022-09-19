package ar.nex.neumatico.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

import ar.nex.neumatico.entity.Neumatico;
import ar.nex.neumatico.service.NeumaticoService;

@RestController
@RequestMapping(value = "/api/neumaticos")
@CrossOrigin
public class NeumaticoController {

    @Autowired
    private NeumaticoService NeumaticoService;

    @GetMapping
    public ResponseEntity<List<Neumatico>> listNeumatico(
            @RequestParam(name = "categoryId", required = false) Long categoryId) {
        List<Neumatico> Neumaticos = new ArrayList<>();
        if (null == categoryId) {
            Neumaticos = NeumaticoService.listAllNeumatico();
            if (Neumaticos.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
        } else {
            // Neumaticos =
            // NeumaticoService.findByCategory(Category.builder().id(categoryId).build());
            // if (Neumaticos.isEmpty()) {
            // return ResponseEntity.notFound().build();
            // }
            System.out.println("VER");
        }

        return ResponseEntity.ok(Neumaticos);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Neumatico> getNeumatico(@PathVariable("id") Long id) {
        Neumatico Neumatico = NeumaticoService.getNeumatico(id);
        if (null == Neumatico) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(Neumatico);
    }

    @PostMapping
    public ResponseEntity<Neumatico> createNeumatico(@Valid @RequestBody Neumatico Neumatico, BindingResult result) {
        if (result.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, NeumaticoService.formatMessage(result));
        }
        Neumatico NeumaticoCreate = NeumaticoService.createNeumatico(Neumatico);
        return ResponseEntity.status(HttpStatus.CREATED).body(NeumaticoCreate);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Neumatico> updateNeumatico(@PathVariable("id") Long id, @RequestBody Neumatico Neumatico) {
        Neumatico.setId(id);
        Neumatico NeumaticoDB = NeumaticoService.updateNeumatico(Neumatico);
        if (NeumaticoDB == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(NeumaticoDB);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Neumatico> deleteNeumatico(@PathVariable("id") Long id) {
        Neumatico NeumaticoDelete = NeumaticoService.deleteNeumatico(id);
        if (NeumaticoDelete == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(NeumaticoDelete);
    }

    @PutMapping(value = "/{id}/stock")
    public ResponseEntity<Neumatico> updateStockNeumatico(@PathVariable Long id,
            @RequestParam(name = "quantity", required = true) Integer quantity) {
        Neumatico neumatico = NeumaticoService.updateStock(id, quantity);
        if (neumatico == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(neumatico);
    }
}
