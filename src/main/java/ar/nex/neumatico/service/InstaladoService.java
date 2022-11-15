package ar.nex.neumatico.service;

import java.util.List;

import org.springframework.validation.BindingResult;

import ar.nex.neumatico.entity.Instalado;

public interface InstaladoService {

    /* Gets */
    public List<Instalado> listAllInstalado();

    public List<Instalado> listByEquipo(Long equipo_id);

    public Instalado getInstalado(Long id);

    public Instalado getInstaladoByEquipoAndPosicion(Long equipo_id, String posicion);

    /* CRUD */
    public Instalado createInstalado(Instalado instalado);

    public Instalado updateInstalado(Instalado instalado);

    public Instalado deleteInstalado(Long id);

    public String formatMessage(BindingResult result);

}
