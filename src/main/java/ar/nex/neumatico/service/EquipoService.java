package ar.nex.neumatico.service;

import java.util.List;

import org.springframework.validation.BindingResult;

import ar.nex.neumatico.entity.Equipo;
import ar.nex.neumatico.entity.TipoEquipo;

public interface EquipoService {

    /* Gets */
    public List<Equipo> listAllEquipo();

    public List<Equipo> listByTipo(TipoEquipo tipo);

    public Equipo getEquipo(Long id);

    /* CRUD */
    public Equipo createEquipo(Equipo Equipo);

    public Equipo updateEquipo(Equipo Equipo);

    public Equipo deleteEquipo(Long id);

    public String formatMessage(BindingResult result);

}
