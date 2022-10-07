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

import ar.nex.neumatico.entity.Deposito;
import ar.nex.neumatico.service.DepositoService;

@RestController
@RequestMapping(value = "/api/depositos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class DepositoController {

    @Autowired
    private DepositoService depositoService;

    @GetMapping
    public ResponseEntity<List<Deposito>> listDeposito() {
        List<Deposito> depositos = new ArrayList<>();

        depositos = depositoService.listAllDeposito();
        if (depositos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(depositos);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Deposito> getDeposito(@PathVariable("id") Long id) {
        Deposito deposito = depositoService.getDeposito(id);
        if (null == deposito) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(deposito);
    }

    @PostMapping
    public ResponseEntity<Deposito> createDeposito(@Valid @RequestBody Deposito deposito, BindingResult result) {
        if (result.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, depositoService.formatMessage(result));
        }
        Deposito depositoCreate = depositoService.createDeposito(deposito);
        return ResponseEntity.status(HttpStatus.CREATED).body(depositoCreate);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Deposito> updateDeposito(@PathVariable("id") Long id, @RequestBody Deposito deposito) {
        deposito.setId(id);
        Deposito depositoDB = depositoService.updateDeposito(deposito);
        if (depositoDB == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(depositoDB);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Deposito> deleteDeposito(@PathVariable("id") Long id) {
        Deposito depositoDelete = depositoService.deleteDeposito(id);
        if (depositoDelete == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(depositoDelete);
    }

}
