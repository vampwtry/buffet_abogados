package com.project.abogados.services;

import com.project.abogados.model.Roles;
import com.project.abogados.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolService {

    @Autowired
    private RolRepository rolRepository;

    public List<Roles> listaRoles(){
        return rolRepository.findAll();
    }
}
