package ar.nex.neumatico.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ar.nex.neumatico.entity.Deposito;
import ar.nex.neumatico.entity.Medida;
import ar.nex.neumatico.entity.Neumatico;
import ar.nex.neumatico.entity.StockNeumatico;
import ar.nex.neumatico.entity.TipoEstado;

@Repository
public interface NeumaticoRepository extends JpaRepository<Neumatico, Long> {

        @Query(value = "SELECT " +
                        "new ar.nex.neumatico.entity.StockNeumatico(m.id, m.name, n.uso, COUNT(n.id), m.info) " +
                        "FROM Neumatico AS n  " +
                        "LEFT JOIN Medida AS m ON m.id = n.medida " +
                        "WHERE n.estado = ?1 " +
                        "GROUP BY m.id, n.uso")
        List<StockNeumatico> getStock(TipoEstado estado);

        @Query(value = "SELECT " +
                        "new ar.nex.neumatico.entity.StockNeumatico(m.id, m.name, n.uso, COUNT(n.id), m.info) " +
                        "FROM Neumatico AS n  " +
                        "LEFT JOIN Medida AS m ON m.id = n.medida " +
                        "GROUP BY m.id, n.uso")
        List<StockNeumatico> getStockAll();

        List<Neumatico> findByEstado(TipoEstado estado);

        List<Neumatico> findByDeposito(Deposito deposito);

        List<Neumatico> findByDepositoAndMedida(Deposito deposito, Medida medida);

}
