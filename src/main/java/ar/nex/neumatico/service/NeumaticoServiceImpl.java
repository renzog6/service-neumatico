package ar.nex.neumatico.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ar.nex.neumatico.entity.Deposito;
import ar.nex.neumatico.entity.ErrorMessage;
import ar.nex.neumatico.entity.Medida;
import ar.nex.neumatico.entity.Neumatico;
import ar.nex.neumatico.entity.StockNeumatico;
import ar.nex.neumatico.entity.TipoEstado;
import ar.nex.neumatico.repository.NeumaticoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NeumaticoServiceImpl implements NeumaticoService {

    private final NeumaticoRepository neumaticoRepository;

    @Override
    public List<Neumatico> listAllNeumatico() {
        return neumaticoRepository.findAll();
    }

    @Override
    public List<Neumatico> listByEstado(TipoEstado estado) {
        return neumaticoRepository.findByEstado(estado);
    }

    @Override
    public List<Neumatico> listByDeposito(Deposito deposito) {
        return neumaticoRepository.findByDeposito(deposito);
    }

    @Override
    public List<Neumatico> listByDepositoAndMedida(Deposito deposito, Medida medida) {
        return neumaticoRepository.findByDepositoAndMedida(deposito, medida);
    }

    @Override
    public Neumatico getNeumatico(Long id) {
        return neumaticoRepository.findById(id).orElse(null);
    }

    @Override
    public Neumatico createNeumatico(Neumatico neumatico) {
        return neumaticoRepository.save(neumatico);
    }

    @Override
    public Neumatico updateNeumatico(Neumatico neumatico) {
        Neumatico neumaticoDB = getNeumatico(neumatico.getId());
        if (null == neumaticoDB) {
            return null;
        }
        neumaticoDB.setName(neumatico.getName());
        neumaticoDB.setMedida(neumatico.getMedida());
        neumaticoDB.setMarca(neumatico.getMarca());
        neumaticoDB.setModelo(neumatico.getModelo());
        neumaticoDB.setUso(neumatico.getUso());
        neumaticoDB.setInfo(neumatico.getInfo());
        neumaticoDB.setEstado(neumatico.getEstado());
        neumaticoDB.setDeposito(neumatico.getDeposito());
        neumaticoDB.setUpdateAt(neumatico.getUpdateAt());
        return neumaticoRepository.save(neumaticoDB);
    }

    @Override
    public Neumatico deleteNeumatico(Long id) {
        Neumatico neumaticoDB = getNeumatico(id);
        if (null == neumaticoDB) {
            return null;
        }
        // neumaticoDB.setStatus("DELETED");
        neumaticoRepository.delete(neumaticoDB);
        return neumaticoDB;
    }

    @Override
    public List<StockNeumatico> getStock(String estado) {
        if (Objects.equals("Todo", estado)) {
            return neumaticoRepository.getStockAll();
        } else {
            return neumaticoRepository.getStock(TipoEstado.valueOf(estado));
        }

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
