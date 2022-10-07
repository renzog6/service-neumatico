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

import ar.nex.neumatico.entity.Marca;
import ar.nex.neumatico.service.MarcaService;

@RestController
@RequestMapping(value = "/api/marcas")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MarcaController {

    @Autowired
    private MarcaService marcaService;

    @GetMapping
    public ResponseEntity<List<Marca>> listMarca() {
        List<Marca> marcas = new ArrayList<>();

        marcas = marcaService.listAllMarca();
        if (marcas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(marcas);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Marca> getMarca(@PathVariable("id") Long id) {
        Marca marca = marcaService.getMarca(id);
        if (marca == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(marca);
    }

    @PostMapping
    public ResponseEntity<Marca> createMarca(@Valid @RequestBody Marca marca, BindingResult result) {
        if (result.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, marcaService.formatMessage(result));
        }
        Marca marcaCreate = marcaService.createMarca(marca);
        return ResponseEntity.status(HttpStatus.CREATED).body(marcaCreate);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Marca> updateMarca(@PathVariable("id") Long id, @RequestBody Marca marca) {
        marca.setId(id);
        Marca marcaDB = marcaService.updateMarca(marca);
        if (marcaDB == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(marcaDB);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Marca> deleteMarca(@PathVariable("id") Long id) {
        Marca marcaDelete = marcaService.deleteMarca(id);
        if (marcaDelete == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(marcaDelete);
    }

}
