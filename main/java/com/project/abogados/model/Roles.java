package com.project.abogados.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
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


    @OneToMany(mappedBy = "rol", cascade = {CascadeType.MERGE, CascadeType.PERSIST} , fetch = FetchType.LAZY)
    private Set<Usuarios> usuarios = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estado_id")
    @JsonIgnore
    private Estados estados;
}
