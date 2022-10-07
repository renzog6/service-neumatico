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

import ar.nex.neumatico.entity.Medida;
import ar.nex.neumatico.service.MedidaService;

@RestController
@RequestMapping(value = "/api/medidas")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MedidaController {

    @Autowired
    private MedidaService medidaService;

    @GetMapping
    public ResponseEntity<List<Medida>> listMedida() {
        List<Medida> medidas = new ArrayList<>();

        medidas = medidaService.listAllMedida();
        if (medidas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(medidas);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Medida> getMedida(@PathVariable("id") Long id) {
        Medida medida = medidaService.getMedida(id);
        if (null == medida) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(medida);
    }

    @PostMapping
    public ResponseEntity<Medida> createMedida(@Valid @RequestBody Medida medida, BindingResult result) {
        if (result.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, medidaService.formatMessage(result));
        }
        Medida medidaCreate = medidaService.createMedida(medida);
        return ResponseEntity.status(HttpStatus.CREATED).body(medidaCreate);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Medida> updateMedida(@PathVariable("id") Long id, @RequestBody Medida medida) {
        medida.setId(id);
        Medida medidaDB = medidaService.updateMedida(medida);
        if (medidaDB == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(medidaDB);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Medida> deleteMedida(@PathVariable("id") Long id) {
        Medida medidaDelete = medidaService.deleteMedida(id);
        if (medidaDelete == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(medidaDelete);
    }

}
