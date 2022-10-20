package ar.nex.neumatico.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class StockNeumatico {

    private Long id;
    private String medida;
    private TipoUso uso;
    private Long stock;
    private String info;

    public StockNeumatico() {
    };
    /*
     * public StockNeumatico(Long id, String medida, int stock) {
     * this.id = id;
     * this.medida = medida;
     * this.stock = stock;
     * }
     */
}
