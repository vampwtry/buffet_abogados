package com.project.abogados.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CasosInformalesDTO {
    private Long Id_casoInformal;
    private String nombresCompletos;
    private String correo;
    private int telefono;
    private String descripcion;
    private LocalDateTime fechaRegistro;
    private Long Id_tipoAbogado;
    private String tipoAbogado;




    public CasosInformalesDTO(String nombresCompletos, String correo, LocalDateTime fechaRegistro) {
        this.nombresCompletos = nombresCompletos;
        this.correo = correo;
        this.fechaRegistro = fechaRegistro;
    }

}
