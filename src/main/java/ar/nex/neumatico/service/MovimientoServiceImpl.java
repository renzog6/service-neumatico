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
import ar.nex.neumatico.entity.Movimiento;
import ar.nex.neumatico.repository.MovimientoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MovimientoServiceImpl implements MovimientoService {

    private final MovimientoRepository movimientoRepository;

    @Override
    public List<Movimiento> listAllMovimiento() {
        return movimientoRepository.findAll();
    }

    @Override
    public Movimiento getMovimiento(Long id) {
        return movimientoRepository.findById(id).orElse(null);
    }

    @Override
    public Movimiento createMovimiento(Movimiento movimiento) {
        return movimientoRepository.save(movimiento);
    }

    @Override
    public Movimiento updateMovimiento(Movimiento movimiento) {
        Movimiento MovimientoDB = getMovimiento(movimiento.getId());
        if (null == MovimientoDB) {
            return null;
        }

        MovimientoDB.setInfo(movimiento.getInfo());
        return movimientoRepository.save(MovimientoDB);
    }

    @Override
    public Movimiento deleteMovimiento(Long id) {
        Movimiento MovimientoDB = getMovimiento(id);
        if (null == MovimientoDB) {
            return null;
        }
        // MovimientoDB.setStatus("DELETED");
        movimientoRepository.delete(MovimientoDB);
        return MovimientoDB;
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
