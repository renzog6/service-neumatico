package ar.nex.neumatico.service;

import java.util.List;

import org.springframework.validation.BindingResult;

import ar.nex.neumatico.entity.Neumatico;

public interface NeumaticoService {

    /* Gets */
    public List<Neumatico> listAllNeumatico();

    public Neumatico getNeumatico(Long id);

    // public List<Neumatico> findByCategory(Category category);

    /* CRUD */
    public Neumatico createNeumatico(Neumatico Neumatico);

    public Neumatico updateNeumatico(Neumatico Neumatico);

    public Neumatico deleteNeumatico(Long id);

    public Neumatico updateStock(Long id, Integer quantity);

    public String formatMessage(BindingResult result);

}
