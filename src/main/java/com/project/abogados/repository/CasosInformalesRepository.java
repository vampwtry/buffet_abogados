package com.project.abogados.repository;

import com.project.abogados.model.CasosInformales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CasosInformalesRepository extends JpaRepository<CasosInformales,Long> {
}
