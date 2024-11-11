package com.project.abogados.dtos;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class UsuarioDTO {

    private Long id_user;
    private String primerNombre;
    private String segundoNombre;
    private String primerApellido;
    private String segundoApellido;
    private LocalDateTime fechaRegistro;
    private Date fechaModificacion;
    private int numeroTelefono;
    private int numeroDocumento;
    private String correo;
    private String contraseña;
    private Long rolID;
    private  Long estadoID;
    private Long id_tipoDocumento;
    private String tipoDocumento;
    private Long id_abogado;
    private Boolean msjBienvenida;

}
