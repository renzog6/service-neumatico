package ar.nex.neumatico.service;

import java.util.List;

import org.springframework.validation.BindingResult;

import ar.nex.neumatico.entity.Deposito;

public interface DepositoService {

    /* Gets */
    public List<Deposito> listAllDeposito();

    public Deposito getDeposito(Long id);

    /* CRUD */
    public Deposito createDeposito(Deposito deposito);

    public Deposito updateDeposito(Deposito deposito);

    public Deposito deleteDeposito(Long id);

    public String formatMessage(BindingResult result);

}
