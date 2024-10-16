package com.project.abogados.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tiposAbogados")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TiposAbogados {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_tipoAbogado;
    private String nombre;

    @OneToMany(mappedBy = "tiposAbogados", cascade = {CascadeType.MERGE, CascadeType.PERSIST},fetch = FetchType.LAZY)
    private Set<Abogados> abogados = new HashSet<>();


}
