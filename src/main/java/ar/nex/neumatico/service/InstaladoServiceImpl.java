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
import ar.nex.neumatico.entity.Instalado;
import ar.nex.neumatico.repository.InstaladoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InstaladoServiceImpl implements InstaladoService {

    private final InstaladoRepository instaladoRepository;

    @Override
    public List<Instalado> listAllInstalado() {
        return instaladoRepository.findAll();
    }

    @Override
    public List<Instalado> listByEquipo(Long equipo_id) {
        return instaladoRepository.findByEquipoId(equipo_id);
    }

    @Override
    public Instalado getInstalado(Long id) {
        return instaladoRepository.findById(id).orElse(null);
    }

    @Override
    public Instalado getInstaladoByEquipoAndPosicion(Long equipo_id, String posicion) {
        return instaladoRepository.findByEquipoIdAndPosicion(equipo_id, posicion);
    }

    @Override
    public Instalado createInstalado(Instalado instalado) {
        return instaladoRepository.save(instalado);
    }

    @Override
    public Instalado updateInstalado(Instalado instalado) {
        Instalado instaladoDB = getInstalado(instalado.getId());
        if (instaladoDB == null) {
            return null;
        }
        instaladoDB.setPosicion(instalado.getPosicion());
        instaladoDB.setInfo(instalado.getInfo());
        return instaladoRepository.save(instaladoDB);
    }

    @Override
    public Instalado deleteInstalado(Long id) {
        Instalado instaladoDB = getInstalado(id);
        if (instaladoDB == null) {
            return null;
        }
        // InstaladoDB.setStatus("DELETED");
        instaladoRepository.delete(instaladoDB);
        return instaladoDB;
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
