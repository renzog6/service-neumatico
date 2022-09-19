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
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "neumatico")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Neumatico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String equipo;

    @NotEmpty(message = "La MEDIDA no puede ser vacia.")
    private String medida;

    @NotNull(message = "La MARCA no puede ser vacia.")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "marca_id")
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    private Marca marca;

    private String modelo;
    private String posicion;
    private Integer stock;

    @Column(name = "update_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateAt;

    private String info;
}
