package com.project.abogados.repository;

import com.project.abogados.model.Roles;
import com.project.abogados.model.TiposAbogados;
import jdk.jfr.Registered;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TiposAbogadosRepository extends JpaRepository<TiposAbogados, Long> {

}
