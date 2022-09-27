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

import ar.nex.neumatico.entity.Equipo;
import ar.nex.neumatico.service.EquipoService;

@RestController
@RequestMapping(value = "/api/equipos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class EquipoController {

    @Autowired
    private EquipoService equipoService;

    @GetMapping
    public ResponseEntity<List<Equipo>> listEquipo() {
        List<Equipo> equipos = new ArrayList<>();

        equipos = equipoService.listAllEquipo();
        if (equipos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(equipos);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Equipo> getEquipo(@PathVariable("id") Long id) {
        Equipo equipo = equipoService.getEquipo(id);
        if (null == equipo) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(equipo);
    }

    @PostMapping
    public ResponseEntity<Equipo> createEquipo(@Valid @RequestBody Equipo equipo, BindingResult result) {
        if (result.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, equipoService.formatMessage(result));
        }
        Equipo equipoCreate = equipoService.createEquipo(equipo);
        return ResponseEntity.status(HttpStatus.CREATED).body(equipoCreate);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Equipo> updateEquipo(@PathVariable("id") Long id, @RequestBody Equipo equipo) {
        equipo.setId(id);
        Equipo equipoDB = equipoService.updateEquipo(equipo);
        if (equipoDB == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(equipoDB);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Equipo> deleteEquipo(@PathVariable("id") Long id) {
        Equipo equipoDelete = equipoService.deleteEquipo(id);
        if (equipoDelete == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(equipoDelete);
    }

}
