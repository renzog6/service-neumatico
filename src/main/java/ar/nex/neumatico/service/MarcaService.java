package ar.nex.neumatico.service;

import java.util.List;

import org.springframework.validation.BindingResult;

import ar.nex.neumatico.entity.Marca;

public interface MarcaService {

    /* Gets */
    public List<Marca> listAllMarca();

    public Marca getMarca(Long id);

    /* CRUD */
    public Marca createMarca(Marca marca);

    public Marca updateMarca(Marca marca);

    public Marca deleteMarca(Long id);

    public String formatMessage(BindingResult result);

}
