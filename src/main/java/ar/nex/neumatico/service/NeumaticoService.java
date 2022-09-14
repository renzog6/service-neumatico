package ar.nex.neumatico.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.validation.BindingResult;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ar.nex.neumatico.entity.ErrorMessage;
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
