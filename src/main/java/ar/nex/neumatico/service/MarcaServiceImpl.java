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
import ar.nex.neumatico.entity.Marca;
import ar.nex.neumatico.repository.MarcaRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MarcaServiceImpl implements MarcaService {

    private final MarcaRepository marcaRepository;

    @Override
    public List<Marca> listAllMarca() {
        return marcaRepository.findAll();
    }

    @Override
    public Marca getMarca(Long id) {
        return marcaRepository.findById(id).orElse(null);
    }

    @Override
    public Marca createMarca(Marca marca) {
        return marcaRepository.save(marca);
    }

    @Override
    public Marca updateMarca(Marca marca) {
        Marca marcaDB = getMarca(marca.getId());
        if (marcaDB == null) {
            return null;
        }
        marcaDB.setName(marca.getName());
        marcaDB.setInfo(marca.getInfo());
        return marcaRepository.save(marcaDB);
    }

    @Override
    public Marca deleteMarca(Long id) {
        Marca marcaDB = getMarca(id);
        if (marcaDB == null) {
            return null;
        }
        // MarcaDB.setStatus("DELETED");
        marcaRepository.delete(marcaDB);
        return marcaDB;
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
