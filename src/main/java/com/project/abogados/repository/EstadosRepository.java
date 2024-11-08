package com.project.abogados.repository;

import com.project.abogados.model.Estados;
import com.project.abogados.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EstadosRepository extends JpaRepository<Estados,Long> {
    Optional<Estados> findByNombreEstado(String nombreEstado);

}
