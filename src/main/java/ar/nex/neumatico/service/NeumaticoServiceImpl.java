package ar.nex.neumatico.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ar.nex.neumatico.entity.ErrorMessage;
import ar.nex.neumatico.entity.Neumatico;
import ar.nex.neumatico.repository.NeumaticoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NeumaticoServiceImpl implements NeumaticoService {

    private final NeumaticoRepository NeumaticoRepository;

    @Override
    public List<Neumatico> listAllNeumatico() {
        return NeumaticoRepository.findAll();
    }

    @Override
    public Neumatico getNeumatico(Long id) {
        return NeumaticoRepository.findById(id).orElse(null);
    }

    @Override
    public Neumatico createNeumatico(Neumatico Neumatico) {
        Neumatico.setUpdateAt(new Date());
        return NeumaticoRepository.save(Neumatico);
    }

    @Override
    public Neumatico updateNeumatico(Neumatico Neumatico) {
        Neumatico NeumaticoDB = getNeumatico(Neumatico.getId());
        if (null == NeumaticoDB) {
            return null;
        }
        NeumaticoDB.setEquipo(Neumatico.getEquipo());
        NeumaticoDB.setMedida(Neumatico.getMedida());
        NeumaticoDB.setMarca(Neumatico.getMarca());
        NeumaticoDB.setModelo(Neumatico.getModelo());
        NeumaticoDB.setUpdateAt(new Date());
        return NeumaticoRepository.save(NeumaticoDB);
    }

    @Override
    public Neumatico deleteNeumatico(Long id) {
        Neumatico NeumaticoDB = getNeumatico(id);
        if (null == NeumaticoDB) {
            return null;
        }
        // NeumaticoDB.setStatus("DELETED");
        NeumaticoRepository.delete(NeumaticoDB);
        return NeumaticoDB;
    }

    @Override
    public Neumatico updateStock(Long id, Integer quantity) {
        Neumatico NeumaticoDB = getNeumatico(id);
        if (null == NeumaticoDB) {
            return null;
        }
        Integer stock = NeumaticoDB.getStock() + quantity;
        NeumaticoDB.setStock(stock);
        return NeumaticoRepository.save(NeumaticoDB);
    }

    /**
     * @param result
     * @return
     */
    public String formatMessage(BindingResult result) {
        List<Map<String, String>> errors = result.getFieldErrors().stream()
                .map(err -> {
                    Map<String, String> error = new HashMap<>();
                    error.put(err.getField(), err.getDefaultMessage());
                    return error;

                }).collect(Collectors.toList());
        ErrorMessage errorMessage = ErrorMessage.builder()
                .code("01")
                .messages(errors).build();
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = "";
        try {
            jsonString = mapper.writeValueAsString(errorMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonString;
    }

}
