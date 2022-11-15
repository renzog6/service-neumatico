package ar.nex.neumatico.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Instalado")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Instalado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "No puede ser vacio.")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipo_id")
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    private Equipo equipo;

    @NotNull(message = "No puede ser vacio.")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "neumatico_id")
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    private Neumatico neumatico;

    private String posicion;

    private String info;

    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
}
