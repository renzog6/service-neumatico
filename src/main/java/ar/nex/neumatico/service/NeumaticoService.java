package ar.nex.neumatico.service;

import java.util.List;

import org.springframework.validation.BindingResult;

import ar.nex.neumatico.entity.Deposito;
import ar.nex.neumatico.entity.Medida;
import ar.nex.neumatico.entity.Neumatico;
import ar.nex.neumatico.entity.StockNeumatico;
import ar.nex.neumatico.entity.TipoEstado;

public interface NeumaticoService {

    /* Gets */
    public Neumatico getNeumatico(Long id);

    public List<Neumatico> listAllNeumatico();

    public List<Neumatico> listByEstado(TipoEstado estado);

    public List<Neumatico> listByDeposito(Deposito deposito);

    public List<Neumatico> listByDepositoAndMedida(Deposito deposito, Medida medida);

    /* CRUD */
    public Neumatico createNeumatico(Neumatico Neumatico);

    public Neumatico updateNeumatico(Neumatico Neumatico);

    public Neumatico deleteNeumatico(Long id);

    /* STOCK */
    public List<StockNeumatico> getStock(String estado);

    public String formatMessage(BindingResult result);

}
