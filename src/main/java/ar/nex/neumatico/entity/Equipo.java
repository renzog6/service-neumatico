package ar.nex.neumatico.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Equipo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String patente;

    @Enumerated(EnumType.STRING)
    private TipoEquipo tipo;

    private String chofer;
    private String info;
    private boolean estado;
}
