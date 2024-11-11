package com.project.abogados.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@CrossOrigin(origins = "http://localhost:8080") // Permitir solicitudes desde el frontend
@RestController
@RequestMapping("/api")
public class apiController {

    @Value("${powerautomate.api.url}")
    private String powerAutomateURL;

    @PostMapping("/send-email")
    public ResponseEntity<String> sendEmail(@RequestBody EmailRequest emailRequest) {
        RestTemplate restTemplate = new RestTemplate();

        // Crear la solicitud para Power Automate
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        // Cuerpo del JSON enviado al flujo de Power Automate
        String jsonBody = "{\"email\": \"" + emailRequest.getEmail() + "\"}";
        HttpEntity<String> requestEntity = new HttpEntity<>(jsonBody, headers);
        // Llamar al API de Power Automate
        ResponseEntity<String> response = restTemplate.exchange(
                powerAutomateURL, HttpMethod.POST, requestEntity, String.class);

        return ResponseEntity.ok("Correo enviado. Respuesta de Power Automate: " + response.getBody());
    }

    public static class EmailRequest {
        private String email;

        // Getters y setters
        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }
}
