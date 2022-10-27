package ar.nex.neumatico.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.nex.neumatico.entity.Equipo;
import ar.nex.neumatico.entity.TipoEquipo;

@Repository
public interface EquipoRepository extends JpaRepository<Equipo, Long> {
    public List<Equipo> findByTipo(TipoEquipo tipo);

}
