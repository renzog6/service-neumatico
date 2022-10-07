package ar.nex.neumatico.service;

import java.util.List;

import org.springframework.validation.BindingResult;

import ar.nex.neumatico.entity.Medida;

public interface MedidaService {

    /* Gets */
    public List<Medida> listAllMedida();

    public Medida getMedida(Long id);

    /* CRUD */
    public Medida createMedida(Medida medida);

    public Medida updateMedida(Medida medida);

    public Medida deleteMedida(Long id);

    public String formatMessage(BindingResult result);

}
