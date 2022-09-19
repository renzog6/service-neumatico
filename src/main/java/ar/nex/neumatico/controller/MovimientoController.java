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

import ar.nex.neumatico.entity.Movimiento;
import ar.nex.neumatico.service.MovimientoService;

@RestController
@RequestMapping(value = "/api/movimientos")
@CrossOrigin
public class MovimientoController {

    @Autowired
    private MovimientoService moviminetoService;

    @GetMapping
    public ResponseEntity<List<Movimiento>> listMovimiento() {
        List<Movimiento> movimientos = new ArrayList<>();

        movimientos = moviminetoService.listAllMovimiento();
        if (movimientos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(movimientos);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Movimiento> getMovimiento(@PathVariable("id") Long id) {
        Movimiento movimiento = moviminetoService.getMovimiento(id);
        if (null == movimiento) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(movimiento);
    }

    @PostMapping
    public ResponseEntity<Movimiento> createMovimiento(@Valid @RequestBody Movimiento movimiento,
            BindingResult result) {
        if (result.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, moviminetoService.formatMessage(result));
        }
        Movimiento movimientoCreate = moviminetoService.createMovimiento(movimiento);
        return ResponseEntity.status(HttpStatus.CREATED).body(movimientoCreate);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Movimiento> updateMovimiento(@PathVariable("id") Long id,
            @RequestBody Movimiento movimiento) {
        movimiento.setId(id);
        Movimiento movimientoDB = moviminetoService.updateMovimiento(movimiento);
        if (movimientoDB == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(movimientoDB);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Movimiento> deleteMovimiento(@PathVariable("id") Long id) {
        Movimiento movimiento = moviminetoService.deleteMovimiento(id);
        if (movimiento == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(movimiento);
    }

}
