package ar.nex.neumatico.service;

import java.util.List;

import org.springframework.validation.BindingResult;

import ar.nex.neumatico.entity.Movimiento;

public interface MovimientoService {

    /* Gets */
    public List<Movimiento> listAllMovimiento();

    public Movimiento getMovimiento(Long id);

    /* CRUD */
    public Movimiento createMovimiento(Movimiento movimiento);

    public Movimiento updateMovimiento(Movimiento movimiento);

    public Movimiento deleteMovimiento(Long id);

    public String formatMessage(BindingResult result);

}
