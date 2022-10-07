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
import ar.nex.neumatico.entity.Deposito;
import ar.nex.neumatico.repository.DepositoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DepositoServiceImpl implements DepositoService {

    private final DepositoRepository depositoRepository;

    @Override
    public List<Deposito> listAllDeposito() {
        return depositoRepository.findAll();
    }

    @Override
    public Deposito getDeposito(Long id) {
        return depositoRepository.findById(id).orElse(null);
    }

    @Override
    public Deposito createDeposito(Deposito deposito) {
        return depositoRepository.save(deposito);
    }

    @Override
    public Deposito updateDeposito(Deposito deposito) {
        Deposito DepositoDB = getDeposito(deposito.getId());
        if (null == DepositoDB) {
            return null;
        }
        DepositoDB.setName(deposito.getName());
        DepositoDB.setInfo(deposito.getInfo());
        return depositoRepository.save(DepositoDB);
    }

    @Override
    public Deposito deleteDeposito(Long id) {
        Deposito depositoDB = getDeposito(id);
        if (null == depositoDB) {
            return null;
        }
        // DepositoDB.setStatus("DELETED");
        depositoRepository.delete(depositoDB);
        return depositoDB;
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
