package ar.nex.neumatico.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.nex.neumatico.entity.Neumatico;

public interface NeumaticoRepository extends JpaRepository<Neumatico, Long> {

}
