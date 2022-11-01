package ar.nex.neumatico.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

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
}
