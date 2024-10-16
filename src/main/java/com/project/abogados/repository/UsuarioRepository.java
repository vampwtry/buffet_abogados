package com.project.abogados.repository;

import com.project.abogados.model.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuarios, Long> {
   Optional<Usuarios> findByNumeroDocumento(int numeroDocumento);
   List<Usuarios> findByRol_NombreRol(String nombre);




}
