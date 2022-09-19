package ar.nex.neumatico.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.nex.neumatico.entity.Movimiento;

public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {

}
