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
import ar.nex.neumatico.entity.StockNeumatico;
import ar.nex.neumatico.entity.TipoEstado;
import ar.nex.neumatico.service.NeumaticoService;

@RestController
@RequestMapping(value = "/api/neumaticos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class NeumaticoController {

    @Autowired
    private NeumaticoService neumaticoService;

    @GetMapping
    public ResponseEntity<List<Neumatico>> listNeumatico(
            @RequestParam(name = "categoryId", required = false) Long categoryId) {
        List<Neumatico> Neumaticos = new ArrayList<>();
        if (null == categoryId) {
            Neumaticos = neumaticoService.listAllNeumatico();
            if (Neumaticos.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
        } else {
            // Neumaticos =
            // neumaticoService.findByCategory(Category.builder().id(categoryId).build());
            // if (Neumaticos.isEmpty()) {
            // return ResponseEntity.notFound().build();
            // }
            System.out.println("VER");
        }

        return ResponseEntity.ok(Neumaticos);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Neumatico> getNeumatico(@PathVariable("id") Long id) {
        Neumatico Neumatico = neumaticoService.getNeumatico(id);
        if (null == Neumatico) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(Neumatico);
    }

    @PostMapping
    public ResponseEntity<Neumatico> createNeumatico(@Valid @RequestBody Neumatico neumatico, BindingResult result) {
        if (result.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, neumaticoService.formatMessage(result));
        }
        Neumatico neumaticoCreate = neumaticoService.createNeumatico(neumatico);
        return ResponseEntity.status(HttpStatus.CREATED).body(neumaticoCreate);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Neumatico> updateNeumatico(@PathVariable("id") Long id, @RequestBody Neumatico Neumatico) {
        Neumatico.setId(id);
        Neumatico NeumaticoDB = neumaticoService.updateNeumatico(Neumatico);
        if (NeumaticoDB == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(NeumaticoDB);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Neumatico> deleteNeumatico(@PathVariable("id") Long id) {
        Neumatico NeumaticoDelete = neumaticoService.deleteNeumatico(id);
        if (NeumaticoDelete == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(NeumaticoDelete);
    }

    @PutMapping(value = "/{id}/stock")
    public ResponseEntity<Neumatico> updateStockNeumatico(@PathVariable Long id,
            @RequestParam(name = "quantity", required = true) Integer quantity) {
        Neumatico neumatico = neumaticoService.updateStock(id, quantity);
        if (neumatico == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(neumatico);
    }

    @GetMapping(value = "/stock")
    public ResponseEntity<List<StockNeumatico>> getStockNeumatico(
            @RequestParam(name = "estado", required = false) String estado) {

        List<StockNeumatico> Neumaticos = new ArrayList<>();

        Neumaticos = neumaticoService.getStock(estado);

        if (Neumaticos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(Neumaticos);
    }
}
