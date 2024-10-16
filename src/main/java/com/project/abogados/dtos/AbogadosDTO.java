package com.project.abogados.dtos;
import lombok.Data;


@Data
public class AbogadosDTO {

    private Long id_abogado;
    private String especialidad;
    private String tarjetaProfesional;
    private Long id_user;
    private Long id_tipoAbogado;
}
