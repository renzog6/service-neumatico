package ar.nex.neumatico.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import ar.nex.neumatico.entity.Deposito;
import ar.nex.neumatico.entity.Instalado;
import ar.nex.neumatico.entity.Neumatico;
import ar.nex.neumatico.service.DepositoService;
import ar.nex.neumatico.service.InstaladoService;
import ar.nex.neumatico.service.NeumaticoService;

@RestController
@RequestMapping(value = "/api/instalados")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class InstaladoController {

    @Autowired
    private NeumaticoService neumaticoService;
    @Autowired
    private InstaladoService instaladoService;

    @Autowired
    private DepositoService depositoService;

    @GetMapping
    public ResponseEntity<List<Instalado>> listInstalados(
            @RequestParam(name = "equipo_id", required = false) Long equipo_id) {
        List<Instalado> instalados = new ArrayList<>();
        System.out.println(">>>>>>>>>>>>>>>>>>>>> " + equipo_id);
        if (equipo_id == null) {
            instalados = instaladoService.listAllInstalado();
            if (instalados.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
        } else {
            instalados = instaladoService.listByEquipo(equipo_id);
            if (instalados.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
        }

        return ResponseEntity.ok(instalados);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Instalado> getInstalado(@PathVariable("id") Long id) {
        Instalado instalado = instaladoService.getInstalado(id);
        if (instalado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(instalado);
    }

    @PostMapping
    public ResponseEntity<String> createInstalado(@Valid @RequestBody Instalado instalado, BindingResult result) {

        if (result.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, instaladoService.formatMessage(result));
        }

        Instalado instaladoDB = instaladoService.getInstaladoByEquipoAndPosicion((long) instalado.getEquipo().getId(),
                instalado.getPosicion());
        System.out.println(instaladoDB);
        if (instaladoDB != null) {
            System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
            return ResponseEntity.badRequest()
                    .body("Ya esta un neumatico en esta posicion");
        }
        Instalado instaladoCreate = instaladoService.createInstalado(instalado);
        Neumatico neumaticoDB = neumaticoService.getNeumatico(instaladoCreate.getNeumatico().getId());
        Deposito depositoDB = depositoService.getDeposito((long) 2);

        neumaticoDB.setDeposito(depositoDB);
        neumaticoService.updateNeumatico(neumaticoDB);

        System.out.println("YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY");

        return ResponseEntity.status(HttpStatus.CREATED).body("El neumatico instalado con exito");
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Instalado> updateInstalado(@PathVariable("id") Long id, @RequestBody Instalado instalado) {
        instalado.setId(id);
        Instalado instaladoDB = instaladoService.updateInstalado(instalado);
        if (instaladoDB == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(instaladoDB);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Instalado> deleteInstalado(@PathVariable("id") Long id) {
        Instalado instaladoDelete = instaladoService.deleteInstalado(id);
        if (instaladoDelete == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(instaladoDelete);
    }

}
