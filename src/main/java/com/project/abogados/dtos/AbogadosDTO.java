package com.project.abogados.dtos;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
@Getter
@Setter
public class AbogadosDTO {

    private Long id_abogado;
    private String especialidad;
    private String tarjetaProfesional;
    private Long id_user;
    private Long id_tipoAbogado;
    private String tipoAbogado;
    private String primerNombreUsuario;
    private String primerApellidoUsuario;
}
