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
import ar.nex.neumatico.entity.Medida;
import ar.nex.neumatico.entity.Neumatico;
import ar.nex.neumatico.entity.StockNeumatico;
import ar.nex.neumatico.entity.TipoEstado;
import ar.nex.neumatico.service.DepositoService;
import ar.nex.neumatico.service.MedidaService;
import ar.nex.neumatico.service.NeumaticoService;

@RestController
@RequestMapping(value = "/api/neumaticos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class NeumaticoController {

    @Autowired
    private NeumaticoService neumaticoService;
    @Autowired
    private DepositoService depositoService;

    @Autowired
    private MedidaService medidaService;

    @GetMapping
    public ResponseEntity<List<Neumatico>> listNeumatico(
            @RequestParam(name = "estado", required = false) TipoEstado estado) {
        List<Neumatico> Neumaticos = new ArrayList<>();

        if (estado == null) {
            Neumaticos = neumaticoService.listAllNeumatico();
            if (Neumaticos.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
        } else {
            Neumaticos = neumaticoService.listByEstado(estado);
            if (Neumaticos.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            System.out.println("VER" + estado);
        }

        return ResponseEntity.ok(Neumaticos);
    }

    @GetMapping(value = "/deposito")
    public ResponseEntity<List<Neumatico>> listNeumaticoByDeposito(
            @RequestParam(name = "deposito", required = false) Long deposito_id) {
        List<Neumatico> Neumaticos = new ArrayList<>();

        if (deposito_id == null) {
            Neumaticos = neumaticoService.listAllNeumatico();
            if (Neumaticos.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
        } else {
            Deposito deposito = depositoService.getDeposito(deposito_id);
            Neumaticos = neumaticoService.listByDeposito(deposito);
            if (Neumaticos.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
        }

        return ResponseEntity.ok(Neumaticos);
    }

    @GetMapping(value = "/disponibles")
    public ResponseEntity<List<Neumatico>> listNeumaticoDisponibles(
            @RequestParam(name = "deposito", required = false) Long deposito_id,
            @RequestParam(name = "medida", required = false) Long medida_id) {

        List<Neumatico> Neumaticos = new ArrayList<>();

        if (deposito_id == null) {
            Neumaticos = neumaticoService.listAllNeumatico();
            if (Neumaticos.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
        } else {

            Deposito deposito = depositoService.getDeposito(deposito_id);
            Medida medida = medidaService.getMedida(medida_id);
            Neumaticos = neumaticoService.listByDepositoAndMedida(deposito, medida);

            if (Neumaticos.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
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
