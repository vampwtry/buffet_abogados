package com.project.abogados.services;

import com.project.abogados.dtos.UsuarioDTO;
import com.project.abogados.model.Estados;
import com.project.abogados.model.Roles;
import com.project.abogados.model.TiposDocumentos;
import com.project.abogados.model.Usuarios;
import com.project.abogados.repository.EstadosRepository;
import com.project.abogados.repository.RolRepository;
import com.project.abogados.repository.TiposDocumentosRepository;
import com.project.abogados.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private  RolRepository rolRepository;
    @Autowired
    private EstadosRepository estadosRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private TiposDocumentosRepository tiposDocumentosRepository;



    public void crearUsuario(UsuarioDTO usuarioDTO){
        Usuarios nuevoUsuario = new Usuarios();
        nuevoUsuario.setPrimerNombre(usuarioDTO.getPrimerNombre());
        nuevoUsuario.setPrimerApellido(usuarioDTO.getPrimerApellido());
        nuevoUsuario.setSegundoNombre(usuarioDTO.getSegundoNombre());
        nuevoUsuario.setSegundoApellido(usuarioDTO.getSegundoApellido());
        nuevoUsuario.setNumeroDocumento(usuarioDTO.getNumeroDocumento());
        nuevoUsuario.setCorreo(usuarioDTO.getCorreo());
        nuevoUsuario.setNumeroTelefono(usuarioDTO.getNumeroTelefono());
        nuevoUsuario.setContraseña(passwordEncoder.encode(usuarioDTO.getContraseña()));

        TiposDocumentos tiposDocumentos = tiposDocumentosRepository.findById(usuarioDTO.getId_tipoDocumento())
                .orElseThrow(() -> new RuntimeException("Tipo de documento no encontrado"));
        nuevoUsuario.setTiposDocumentos(tiposDocumentos);
        Roles rol = rolRepository.findByNombreRol("ROLE_USER")
                .orElseThrow(()-> new RuntimeException("Rol no encontrado"));
        nuevoUsuario.setRol(rol);
        Estados estado = estadosRepository.findByNombreEstado("ACTIVO")
                        .orElseThrow(()-> new RuntimeException("Estado no encontrado"));
        nuevoUsuario.setEstados(estado);

        nuevoUsuario.setFechaRegistro(LocalDateTime.now());
        usuarioRepository.save(nuevoUsuario);

    }

    public List<UsuarioDTO> listUsuarios(){
        List<Usuarios> usuarios = usuarioRepository.findAll();
        return usuarios.stream().map(this::transformarDTO).collect(Collectors.toList());
    }

    private UsuarioDTO transformarDTO(Usuarios usuarios){
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId_user(usuarios.getId_user());
        usuarioDTO.setPrimerNombre(usuarios.getPrimerNombre());
        usuarioDTO.setSegundoNombre(usuarios.getSegundoNombre());
        usuarioDTO.setPrimerApellido(usuarios.getPrimerApellido());
        usuarioDTO.setSegundoApellido(usuarios.getSegundoApellido());
        usuarioDTO.setCorreo(usuarios.getCorreo());
        usuarioDTO.setNumeroTelefono(usuarios.getNumeroTelefono());
        usuarioDTO.setFechaRegistro(usuarios.getFechaRegistro());
        usuarioDTO.setEstadoID(usuarios.getEstados().getId_estado());
        if (
                usuarios.getTiposDocumentos()!=null

        ){
            usuarioDTO.setId_tipoDocumento(usuarios.getTiposDocumentos().getId_tipoDocumento());
        }else{
            usuarioDTO.setId_tipoDocumento(null);
        }
        if (
                usuarios.getEstados()!=null

        ){
            usuarioDTO.setEstadoID(usuarios.getEstados().getId_estado());
        }else{
            usuarioDTO.setEstadoID(null);
        }
        usuarioDTO.setNumeroDocumento(usuarios.getNumeroDocumento());

        return usuarioDTO;
    }

    public UsuarioDTO obtenerUsuarioId(Long id){
        Usuarios usuarios = usuarioRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("No se encontro el usuario"));
        UsuarioDTO usuarioDTO = transformarDTO(usuarios);
        return usuarioDTO;
    }

    public void actualizarUsuario(UsuarioDTO usuarioDTO) {
        // Busca el usuario en la base de datos
        Usuarios usuarios = usuarioRepository.findById(usuarioDTO.getId_user())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Actualiza los campos del objeto Usuarios con los valores del DTO
        usuarios.setPrimerNombre(usuarioDTO.getPrimerNombre());
        usuarios.setSegundoNombre(usuarioDTO.getSegundoNombre());
        usuarios.setPrimerApellido(usuarioDTO.getPrimerApellido());
        usuarios.setSegundoApellido(usuarioDTO.getSegundoApellido());
        usuarios.setCorreo(usuarioDTO.getCorreo());
        usuarios.setNumeroDocumento(usuarioDTO.getNumeroDocumento());
        usuarios.setNumeroTelefono(usuarioDTO.getNumeroTelefono());

        // Busca el tipo de documento y estado
        TiposDocumentos tiposDocumentos = tiposDocumentosRepository.findById(usuarioDTO.getId_tipoDocumento())
                .orElseThrow(() -> new RuntimeException("No se encontró el tipo de documento"));
        usuarios.setTiposDocumentos(tiposDocumentos);

        Estados estados = estadosRepository.findById(usuarioDTO.getEstadoID())
                .orElseThrow(() -> new RuntimeException("No se encontró el estado"));
        usuarios.setEstados(estados);

        // Actualiza la fecha de modificación
        usuarios.setFechaModificacion(LocalDateTime.now());

        // Guarda el usuario actualizado en la base de datos
        usuarioRepository.save(usuarios);
    }



}
