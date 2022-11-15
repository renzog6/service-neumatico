package ar.nex.neumatico.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.nex.neumatico.entity.Instalado;

@Repository
public interface InstaladoRepository extends JpaRepository<Instalado, Long> {

    List<Instalado> findByEquipoId(Long equipo_id);

    Instalado findByEquipoIdAndPosicion(Long equipo_id, String posicion);
}
