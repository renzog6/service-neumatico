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
        List<Marca> Marcas = new ArrayList<>();

        Marcas = marcaService.listAllMarca();
        if (Marcas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(Marcas);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Marca> getMarca(@PathVariable("id") Long id) {
        Marca Marca = marcaService.getMarca(id);
        if (null == Marca) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(Marca);
    }

    @PostMapping
    public ResponseEntity<Marca> createMarca(@Valid @RequestBody Marca Marca, BindingResult result) {
        if (result.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, marcaService.formatMessage(result));
        }
        Marca MarcaCreate = marcaService.createMarca(Marca);
        return ResponseEntity.status(HttpStatus.CREATED).body(MarcaCreate);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Marca> updateMarca(@PathVariable("id") Long id, @RequestBody Marca Marca) {
        Marca.setId(id);
        Marca MarcaDB = marcaService.updateMarca(Marca);
        if (MarcaDB == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(MarcaDB);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Marca> deleteMarca(@PathVariable("id") Long id) {
        Marca MarcaDelete = marcaService.deleteMarca(id);
        if (MarcaDelete == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(MarcaDelete);
    }

}
