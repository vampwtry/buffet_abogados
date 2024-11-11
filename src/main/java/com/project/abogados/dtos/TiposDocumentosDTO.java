package com.project.abogados.dtos;

import lombok.Data;

@Data
public class TiposDocumentosDTO {

    private Long id;
    private String nombre;
    private String nomenclatura;
    private String estado;
    private Long id_Estado;
}
