package com.project.abogados.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

@Entity
@Table(name = "casosInformales")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CasosInformales {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long Id_casoInformal;
    private String nombresCompletos;
    private String correo;
    private int telefono;
    private String descripcion;
    private LocalDateTime fechaRegistro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipoAbogados_id")
    @JsonIgnore
    private TiposAbogados tiposAbogados;
}
