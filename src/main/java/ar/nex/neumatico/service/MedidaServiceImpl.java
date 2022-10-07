package ar.nex.neumatico.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ar.nex.neumatico.entity.ErrorMessage;
import ar.nex.neumatico.entity.Medida;
import ar.nex.neumatico.repository.MedidaRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MedidaServiceImpl implements MedidaService {

    private final MedidaRepository medidaRepository;

    @Override
    public List<Medida> listAllMedida() {
        return medidaRepository.findAll();
    }

    @Override
    public Medida getMedida(Long id) {
        return medidaRepository.findById(id).orElse(null);
    }

    @Override
    public Medida createMedida(Medida medida) {
        return medidaRepository.save(medida);
    }

    @Override
    public Medida updateMedida(Medida medida) {
        Medida medidaDB = getMedida(medida.getId());
        if (null == medidaDB) {
            return null;
        }
        medidaDB.setName(medida.getName());
        medidaDB.setInfo(medida.getInfo());
        return medidaRepository.save(medidaDB);
    }

    @Override
    public Medida deleteMedida(Long id) {
        Medida medidaDB = getMedida(id);
        if (null == medidaDB) {
            return null;
        }
        // MedidaDB.setStatus("DELETED");
        medidaRepository.delete(medidaDB);
        return medidaDB;
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
