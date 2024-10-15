package com.project.abogados.repository;

import com.project.abogados.model.TiposDocumentos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TiposDocumentosRepository  extends JpaRepository<TiposDocumentos , Long> {
    Optional<TiposDocumentos> findByNombre(String nombre);
}
