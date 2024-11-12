package com.project.abogados.controller;


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
public class apiRest {
    @Autowired
    private CasosInformalesService casosInformalesService;
    @Autowired
    private TiposAbogadosService tiposAbogadosService;

    @PostMapping("/callApi")
    public String callPowerAutomateApi(@RequestParam("fullName") String fullName,
                                       @RequestParam("email") String email,
                                       @RequestParam("fecha") String fecha,
                                       @RequestParam("tipoEjecucion") String tipoEjecucion,
                                       RedirectAttributes redirectAttributes) {
        try {
            // URL de la API
            String url = "https://prod-115.westus.logic.azure.com:443/workflows/7c1cbb422a934ee5ab3c07121b133011/triggers/manual/paths/invoke?api-version=2016-06-01&sp=%2Ftriggers%2Fmanual%2Frun&sv=1.0&sig=C5v8bY9MdF1xz1Eqloz38JyXwZ-b9majL3PuotIf7n4";

            // Crear datos JSON dinámicos
            String json = """
                {
                    "fullName": "%s",
                    "email": "%s",
                    "fecha": "%s",
                    "tipoEjecucion": "%s"
                }
            """.formatted(fullName,email,fecha, tipoEjecucion);

            // Configuración del cliente HTTP
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            // Llamar a la API
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Pasar los datos al controlador de usuarios
            redirectAttributes.addFlashAttribute("status", response.statusCode());
            redirectAttributes.addFlashAttribute("response", response.body());
            redirectAttributes.addFlashAttribute("success", "Correo de bienvenida enviado al usuario");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error ejecutando la API: " + e.getMessage());
        }
        return "redirect:/admin/usuarios"; // Redirigir al controlador de la vista de usuarios
    }

    @PostMapping("/SaveCasoInformal")
    public String guardarCasoInformal(@ModelAttribute("tipAbog") CasosInformalesDTO casosInformalesDTO,
                                      BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "redirect:/CasoInformal?error";
        }
        CasosInformalesDTO casoGuardado = casosInformalesService.crearCasoInformal(casosInformalesDTO);

        try {
            String fechaActual = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);

            callPowerAutomateApi(   casoGuardado.getNombresCompletos(),
                                    casoGuardado.getCorreo(),
                                    fechaActual,
                                    "usrCasoInformal",
                    redirectAttributes);

            redirectAttributes.addFlashAttribute("success", "Caso creado y enviado correctamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al enviar los datos a la API: " + e.getMessage());
        }

        return "redirect:/CasoInformal?success";
    }




}
