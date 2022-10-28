package ar.nex.neumatico.service;

import java.util.List;

import org.springframework.validation.BindingResult;

import ar.nex.neumatico.entity.Neumatico;
import ar.nex.neumatico.entity.StockNeumatico;
import ar.nex.neumatico.entity.TipoEstado;

public interface NeumaticoService {

    /* Gets */
    public List<Neumatico> listAllNeumatico();

    public Neumatico getNeumatico(Long id);

    public List<Neumatico> listByEstado(TipoEstado estado);

    /* CRUD */
    public Neumatico createNeumatico(Neumatico Neumatico);

    public Neumatico updateNeumatico(Neumatico Neumatico);

    public Neumatico deleteNeumatico(Long id);

    /* STOCK */
    public Neumatico updateStock(Long id, Integer quantity);

    public List<StockNeumatico> getStock(String estado);

    public String formatMessage(BindingResult result);

}
