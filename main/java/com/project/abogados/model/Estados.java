package com.project.abogados.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "estados")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Estados {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_estado;

    private String nombreEstado;

    @OneToMany(mappedBy = "estados", cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    private Set<Usuarios> usuarios = new HashSet<>();

    @OneToMany(mappedBy = "estados", cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    private Set<Roles> roles = new HashSet<>();

    @OneToMany(mappedBy = "estados", cascade ={CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    private Set<TiposDocumentos> tiposDocumentos = new HashSet<>();

}
