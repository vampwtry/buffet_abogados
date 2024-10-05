package com.project.abogados.config;

import com.project.abogados.model.Roles;
import com.project.abogados.model.Usuarios;
import com.project.abogados.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService{
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        int numeroDocumento = Integer.parseInt(username);
        Optional<Usuarios> optionalUsuario = usuarioRepository.findByNumeroDocumento(numeroDocumento);
        Usuarios usuario = optionalUsuario.orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con número de documento: " + username));

        Roles rol = usuario.getRol();

        if (rol == null) {
            throw new UsernameNotFoundException("El usuario no tiene un rol asignado.");
        }

        System.out.println("Rol cargado: " + rol.getNombreRol());

        Collection<? extends GrantedAuthority> authorities = getAuthorities(rol);
        return new org.springframework.security.core.userdetails.User(
                String.valueOf(usuario.getNumeroDocumento()),
                usuario.getContraseña(),
                authorities
        );
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Roles rol) {
        return Collections.singletonList(new SimpleGrantedAuthority(rol.getNombreRol()));
    }

}
