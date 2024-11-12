package com.project.abogados.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.abogados.dtos.AbogadosDTO;
import com.project.abogados.dtos.UsuarioDTO;
import com.project.abogados.model.Roles;
import com.project.abogados.model.TiposAbogados;
import com.project.abogados.repository.AbogadoRepository;
import com.project.abogados.repository.RolRepository;
import com.project.abogados.repository.TiposAbogadosRepository;
import com.project.abogados.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import com.project.abogados.dtos.CasosInformalesDTO;
import com.project.abogados.services.CasosInformalesService;
import com.project.abogados.services.TiposAbogadosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/abogados")
public class abogdosController {
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private TiposAbogadosRepository tiposAbogadosRepository;
    @Autowired
    private TiposAbogadosService tiposAbogadosService;
    @Autowired
    private AbogadoRepository abogadoRepository;
    @Autowired
    private AbogadosService abogadosService;
    @Autowired
    private RolRepository rolRepository;


    @GetMapping("/Disponibles")
    public String crearAbogado(Model model){
        List<UsuarioDTO> usuarioRol = usuarioService.obtenerUsers("ROLE_USER");
        model.addAttribute("usuarios", usuarioRol);
        return "admin/layauts/Abogados/CrearAbogado";
    }

    @GetMapping("/agregar/{id}")
    public String agregarAbogado(@PathVariable("id") Long IdUsuario, Model model){
        UsuarioDTO usuarioDTO = usuarioService.obtenerUsuarioId(IdUsuario);
        List<TiposAbogados> tiposAbogados = tiposAbogadosService.listTiposAbogados();
        AbogadosDTO abogadoDTO = new AbogadosDTO();
        abogadoDTO.setId_user(usuarioDTO.getId_user());
        model.addAttribute("abogado", abogadoDTO);
        model.addAttribute("tipAbogados", tiposAbogados);
        model.addAttribute("usuario", usuarioDTO);

        return "admin/layauts/Abogados/nuevoAbogado";
    }

    @PostMapping("/guardar")
    public String guardarAbogado(@ModelAttribute("abogado") AbogadosDTO abogadosDTO,
                                 @RequestParam("documento") MultipartFile documento,
                                 BindingResult result,
                                 Model model,
                                 RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            List<TiposAbogados> tiposAbogados = tiposAbogadosService.listTiposAbogados();
            model.addAttribute("tipAbogados", tiposAbogados);
            return "redirect:/admin/abogados?error";
        }

        try {

            AbogadosDTO abogadoGuardado = abogadosService.crearAbogado(abogadosDTO);


            UsuarioDTO usuarioDTO = usuarioService.obtenerUsuarioId(abogadosDTO.getId_user());
            Optional<Roles> rolAbogado = rolRepository.findByNombreRol("ROLE_ABOGADO");

            if (rolAbogado.isPresent()) {
                usuarioDTO.setRolID(rolAbogado.get().getIdRol());
                usuarioService.actualizarRolUsuario(usuarioDTO);
            } else {
                redirectAttributes.addFlashAttribute("error", "No se encontró el rol de abogado.");
                return "redirect:/admin/abogados";
            }

            // Preparar datos para enviar a Power Automate
            String fechaActual = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE);
            String numeroDoc = String.valueOf(abogadoGuardado.getNumDoc());

            callPowerAutomateApi(
                    abogadoGuardado.getTipoAbogado(),
                    numeroDoc,
                    abogadoGuardado.getTarjetaProfesional(),
                    "DocumentAbogado",
                    documento
            );

            redirectAttributes.addFlashAttribute("success", "Abogado creado y datos enviados correctamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al guardar el abogado o enviar datos a la API: " + e.getMessage());
            return "redirect:/admin/abogados?error";
        }

        return "redirect:/admin/abogados?success";
    }





    @GetMapping("/detalle/{id}")
    public String detalleAbogado(@PathVariable("id") Long id, Model model) {
        AbogadosDTO abogadosDTO = abogadosService.obtenerAbogadoId(id);
        model.addAttribute("abogado", abogadosDTO);
        return "redirect:admin/layauts/abogados";
    }




    public void callPowerAutomateApi(String fullName, String email, String fecha, String tipoEjecucion, MultipartFile documento) throws Exception {
        String url = "https://prod-115.westus.logic.azure.com:443/workflows/7c1cbb422a934ee5ab3c07121b133011/triggers/manual/paths/invoke?api-version=2016-06-01&sp=%2Ftriggers%2Fmanual%2Frun&sv=1.0&sig=C5v8bY9MdF1xz1Eqloz38JyXwZ-b9majL3PuotIf7n4";

        // Validar y construir datos
        Map<String, String> datos = new HashMap<>();
        datos.put("fullName", fullName != null ? fullName : "");
        datos.put("email", email != null ? email : "");
        datos.put("fecha", fecha != null ? fecha : "");
        datos.put("tipoEjecucion", tipoEjecucion != null ? tipoEjecucion : "");

        if (documento != null && !documento.isEmpty()) {
            byte[] bytes = documento.getBytes();
            String base64Documento = Base64.getEncoder().encodeToString(bytes);
            datos.put("nombreArchivo", documento.getOriginalFilename());
            datos.put("tipoArchivo", documento.getContentType());
            datos.put("contenidoBase64", base64Documento);
        } else {
            datos.put("nombreArchivo", "");
            datos.put("tipoArchivo", "");
            datos.put("contenidoBase64", "");
        }

        // Convertir datos a JSON
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(datos);

        // Configurar y realizar la solicitud HTTP
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Validar respuesta
        if (response.statusCode() != 202) {
            throw new Exception("Error al enviar los datos a Power Automate: " + response.body());
        }
    }



}
