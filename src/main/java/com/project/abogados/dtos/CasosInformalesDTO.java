package com.project.abogados.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CasosInformalesDTO {
    private Long Id_casoInformal;
    private String nombresCompletos;
    private String correo;
    private int telefono;
    private String descripcion;
    private LocalDateTime fechaRegistro;
    private Long Id_tipoAbogado;
    private String tipoAbogado;
}
