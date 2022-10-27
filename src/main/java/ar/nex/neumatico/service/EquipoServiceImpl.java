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
import ar.nex.neumatico.entity.TipoEquipo;
import ar.nex.neumatico.entity.Equipo;
import ar.nex.neumatico.repository.EquipoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EquipoServiceImpl implements EquipoService {

    private final EquipoRepository equipoRepository;

    @Override
    public List<Equipo> listAllEquipo() {
        return equipoRepository.findAll();
    }

    @Override
    public List<Equipo> listByTipo(TipoEquipo tipo) {
        return equipoRepository.findByTipo(tipo);
    }

    @Override
    public Equipo getEquipo(Long id) {
        return equipoRepository.findById(id).orElse(null);
    }

    @Override
    public Equipo createEquipo(Equipo Equipo) {
        return equipoRepository.save(Equipo);
    }

    @Override
    public Equipo updateEquipo(Equipo Equipo) {
        Equipo EquipoDB = getEquipo(Equipo.getId());
        if (null == EquipoDB) {
            return null;
        }
        EquipoDB.setName(Equipo.getName());
        EquipoDB.setInfo(Equipo.getInfo());
        return equipoRepository.save(EquipoDB);
    }

    @Override
    public Equipo deleteEquipo(Long id) {
        Equipo EquipoDB = getEquipo(id);
        if (null == EquipoDB) {
            return null;
        }
        // EquipoDB.setStatus("DELETED");
        equipoRepository.delete(EquipoDB);
        return EquipoDB;
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
