package com.project.abogados.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tiposDocumento")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TiposDocumentos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_tipoDocumento;
    private String nombre;
    private String nomenclatura;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estado_id")
    @JsonIgnore
    private Estados estados;

    @OneToMany(mappedBy = "tiposDocumentos", cascade ={CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    private Set<Usuarios> usuarios = new HashSet<>();

}
