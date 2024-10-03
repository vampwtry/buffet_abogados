package com.project.abogados.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Roles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idRol;

    @Column(name = "nombre_rol")
    private String nombreRol;

    @Column(name = "estado_rol")
    private Boolean estadoRol;


    @OneToMany(mappedBy = "rol", cascade = {CascadeType.MERGE, CascadeType.PERSIST} , fetch = FetchType.LAZY)
    private Set<Usuarios> usuarios = new HashSet<>();
}
