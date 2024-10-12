package com.project.abogados.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "usuarios")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuarios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_user;

    private String primerNombre;
    private String segundoNombre;
    private String primerApellido;
    private String segundoApellido;
    private LocalDateTime fechaRegistro;
    private LocalDateTime fechaModificacion;
    private int numeroTelefono;
    private int numeroDocumento;
    private String correo;
    private String contraseña;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rol_id")
    @JsonIgnore //Esta anotacion hace que el cuerpo JSON ignore el campo de roles, si se quiere mostrar el campo de roles se debe cambiar la relaciona EAGER.
    private Roles rol;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estado_id")
    @JsonIgnore
    private Estados estados;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipoDocumento_id")
    @JsonIgnore
    private TiposDocumentos tiposDocumentos;



}
