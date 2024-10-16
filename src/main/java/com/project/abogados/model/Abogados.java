package com.project.abogados.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.*;

@Entity
@Table(name = "abogados")
@Data
@AllArgsConstructor
@NoArgsConstructor


public class Abogados {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_abogado;

    private String especialidad;
    private String tarjetaProfesional;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user")
    @JsonIgnore //Esta anotacion hace que el cuerpo JSON ignore el campo de roles, si se quiere mostrar el campo de roles se debe cambiar la relaciona EAGER.
    private Usuarios user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipoAbogado_id")
    @JsonIgnore
    private TiposAbogados tiposAbogados;

}
