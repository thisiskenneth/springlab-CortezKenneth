package edu.espe.springlab.web.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class InfoController {

    // Obtiene la versión definida en application.yml o en variables de entorno
    @Value("${app.version}")
    private String appVersion;

    // Expone un endpoint simple para consultar la versión desplegada
    @GetMapping("/version")
    public String getVersion() {
        return "App Version: " + appVersion;
    }
}
